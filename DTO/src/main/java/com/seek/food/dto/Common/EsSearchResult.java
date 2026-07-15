package com.seek.food.dto.Common;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsSearchResult<T> {
    private List<T> results=new ArrayList<>();
    private List<Object> lastSortValues;

    public static <T> EsSearchResult<T> success(List<Hit<T>> hits) {
        EsSearchResult<T> esSearchResult = new EsSearchResult<>();
        for (Hit<T> hit:hits) esSearchResult.getResults().add(hit.source());
        //获取SearchAfter的值
        List<FieldValue> lastSortValues = hits.getLast().sort();
        esSearchResult.setLastSortValues(Collections.singletonList(lastSortValues));
        return esSearchResult;
    }

    public List<FieldValue> toFieldValues() {
        List<FieldValue> fieldValues = new ArrayList<>();
        for (Object value : lastSortValues) fieldValues.add((FieldValue) value);
        return fieldValues;
    }
}
