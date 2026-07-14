package com.seek.food.util.Es;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.Script;
import co.elastic.clients.elasticsearch._types.ScriptSource;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.json.JsonData;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class EsUtil{
    //返回一个对某一字段自行自增自减的请求
    public static UpdateRequest getUpdateStepRequest(String index,String id
    ,String field,int step){
        //构建 ScriptSource
        ScriptSource scriptSource = ScriptSource.of(s -> s
                .scriptString("""
                    if (ctx._source.containsKey(params.field)) {
                        ctx._source[params.field] += params.step;
                    } else {
                        ctx._source[params.field] = params.step;
                    }
                """)
        );
        //构建 Script,并且将参数填充
        Script script = Script.of(s -> s
                .source(scriptSource)
                .params(Map.of(
                        "field", JsonData.of(field),
                        "step", JsonData.of(step)
                ))
        );
        //构建 UpdateRequest并且返回
        return UpdateRequest.of(u -> u
                .index(index)
                .id(id)
                .script(script)
        );
    }

    //快速新增
    public static void quickInsert(ElasticsearchClient esClient, String index, long id, Object value){
        try {
            esClient.index(i -> i.index(index)
                    .id(""+id)
                    .document(value));
        } catch (IOException e) {
            log.error("发生错误：",e);
            throw new BizException(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    //快速进行全量更新
    public static void quickUpdate(ElasticsearchClient esClient, String index, long docId, Object value){
        try{
            esClient.update(u -> u
                    .index("test_index")
                    .id(""+docId)
                    // 传入完整对象，全量覆盖原有文档字段
                    .doc(value)
                    // 关键：关闭自动新增，文档不存在直接返回not found
                    .docAsUpsert(false)
                    // 并发乐观锁冲突自动重试3次
                    .retryOnConflict(3) ,Void.class);
        } catch (IOException e) {
            log.error("ES更新IO异常 index:{}, id:{}", index, docId, e);
            throw new BizException(ErrorCodeEnum.SERVER_ERROR);
        }catch (ElasticsearchException e) {
            // 文档不存在/索引不存在都会返回404
            if (e.status() == 404) return;
            // 其他ES服务异常（版本冲突、集群不可用等）
            log.error("ES服务执行异常 index:{}, id:{}", index, docId, e);
            throw new BizException(ErrorCodeEnum.SERVER_ERROR);
        } catch (Exception e) {
            log.error("ES更新未知异常 index:{}, id:{}", index, docId, e);
            throw new BizException(ErrorCodeEnum.SERVER_ERROR);
        }
    }

    //快速删除
    public static void quickDelete(ElasticsearchClient esClient, String index, long docId) throws IOException {
        esClient.delete(d -> d
                .index("test_index")
                .id(""+docId)
        );
    }

}
