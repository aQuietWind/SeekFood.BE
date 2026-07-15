package com.seek.food.merchant.Service.Impl;

import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.dto.Common.EsSearchResult;
import com.seek.food.dto.Merchant.MerchantEsDTO;
import com.seek.food.merchant.Mapper.SearchMapper;
import com.seek.food.merchant.Service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchMapper searchMapper;
    private final CommonParamRulesConfig commonParamRulesConfig;

    @Autowired
    public SearchServiceImpl(SearchMapper searchMapper, CommonParamRulesConfig commonParamRulesConfig) {
        this.searchMapper = searchMapper;
        this.commonParamRulesConfig = commonParamRulesConfig;
    }

    //主动推送
    @Override
    public EsSearchResult<MerchantEsDTO> searchMerchant(int need, int distance, double lon, double lat, long seed
    , int shouldAmount,Double docScore,Long docId) {
        commonParamRulesConfig.lonAndLatCheck(lon, lat);
        commonParamRulesConfig.needNumberCheck(need);
        commonParamRulesConfig.seedNumberCheck(seed);
        commonParamRulesConfig.shouldAmountCheck(shouldAmount);
        return searchMapper.feedMerchant(lon,lat,distance,seed,need,shouldAmount,docScore,docId);
    }
}
