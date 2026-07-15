package com.seek.food.merchant.EsRepository;

import com.seek.food.dto.Merchant.MerchantEsDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends ElasticsearchRepository<MerchantEsDTO, Long> {
    // 自动根据方法名生成查询：根据商户名称分页
    Page<MerchantEsDTO> findByMerchantName(String merchantName, Pageable pageable);
}