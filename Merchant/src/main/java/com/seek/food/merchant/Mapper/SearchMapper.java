package com.seek.food.merchant.Mapper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionBoostMode;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.seek.food.config.NacosConfig.Merchant.MerchantEsTableConfig;
import com.seek.food.dto.Common.EsSearchResult;
import com.seek.food.dto.Merchant.MerchantEsDTO;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Slf4j
public class SearchMapper {

    private final MerchantEsTableConfig merchantEsTableConfig;
    private final ElasticsearchClient esClient;

    @Autowired
    SearchMapper(MerchantEsTableConfig merchantEsTableConfig, ElasticsearchClient esClient) {
        this.merchantEsTableConfig = merchantEsTableConfig;
        this.esClient = esClient;
    }

    //获取随机推送的提问
    public EsSearchResult<MerchantEsDTO> feedMerchant(double lon, double lat, int distance , long seed, int need, int shouldAmount
            ,Double docScore,Long docId) {
        SearchRequest request=getFeedRequest(lon,lat,distance,seed,need,shouldAmount,docScore,docId);
        //根据搜索请求的模板来发送请求
        SearchResponse<MerchantEsDTO> response= null;
        try {
            response = esClient.search(request, MerchantEsDTO.class);
        } catch (Exception e) {
            log.error("推送商家出现异常{}",e.getMessage());
            throw new RuntimeException(e);
        }
        //返回结果
        return EsSearchResult.success(response.hits().hits());
    }

    private SearchRequest getFeedRequest(double lon,double lat,int distance , long seed, int need,int shouldAmount
    ,Double docScore,Long docId){
        SearchRequest.Builder builder=new SearchRequest.Builder()
                .index(merchantEsTableConfig.getIndexName())
                .query(q -> q.functionScore(fs -> fs
                        //基础查询
                        .query(q1 -> q1.bool(b -> b
                                //必须开业
                                .filter(mn -> mn.term(t -> t
                                        .field(merchantEsTableConfig.getIsOpen())
                                        .value(true)
                                ))
                                //必须在范围内
                                .must(mu -> mu.geoDistance(g -> g
                                        .field(merchantEsTableConfig.getMerchantLocation())
                                        // 中心点：用户当前的经纬度
                                        .location(loc -> loc.latlon(ll -> ll.lat(lat).lon(lon)))
                                        // 半径：查询范围内的文档（单位：km/m）
                                        .distance(distance+"km")
                                ))
                                //最好是高收藏的
                                .should(sh -> sh.range(r -> r
                                        .date(d-> d.field(merchantEsTableConfig.getMerchantCollectAmount())
                                                .gte(""+shouldAmount)      //大于等于
                                        )))
                        ))
                        //定义重算分
                        .functions(f -> f
                                //确保种子固定时可以通过search来查询，并且权重随种子一起变化
                                .weight((double) (seed%10))     //权重，即种子的加分范围(0,weight)
                                .randomScore(r -> r.seed(""+seed).field(merchantEsTableConfig.getMerchantId()))
                        )
                        //随机分+算分主导的最终分
                        .boostMode(FunctionBoostMode.Sum)
                ))
                .size(need);
        //当作第一次来查询
        if (docScore==null||docId==null) return builder
                .sort(s -> s.field(f -> f.field("_score").order(SortOrder.Desc)))
                .sort(s -> s.field(f -> f.field(merchantEsTableConfig.getMerchantId()).order(SortOrder.Desc)))
                .build();
        //进行SearchAfter查询
        List<FieldValue> fieldValues=new ArrayList<>();
        fieldValues.add(FieldValue.of(docScore));
        fieldValues.add(FieldValue.of(docId));
        return builder
                .sort(s -> s.field(f -> f.field("_score").order(SortOrder.Desc)))
                .sort(s -> s.field(f -> f.field(merchantEsTableConfig.getMerchantId()).order(SortOrder.Desc)))
                .searchAfter(fieldValues)
                .build();
    }


}
