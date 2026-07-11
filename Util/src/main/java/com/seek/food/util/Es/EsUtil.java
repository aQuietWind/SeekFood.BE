package com.seek.food.util.Es;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
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
public class EsUtil {
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

        // 3. 构建 UpdateRequest并且返回
        return UpdateRequest.of(u -> u
                .index(index)
                .id(id)
                .script(script)
        );
    }

    //快速构建新增脚本
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
}
