package com.seek.food.merchant.Service;

import com.seek.food.dto.Common.EsSearchResult;
import com.seek.food.dto.Merchant.MerchantEsDTO;


public interface SearchService {
    public EsSearchResult<MerchantEsDTO> searchMerchant(int need, int distance, double lon, double lat, long seed, int shouldAmount
            ,Double docScore,Long docId);
}
