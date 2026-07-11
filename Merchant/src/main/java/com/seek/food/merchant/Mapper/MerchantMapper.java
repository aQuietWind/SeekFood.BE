package com.seek.food.merchant.Mapper;

import com.seek.food.dto.Merchant.MerchantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MerchantMapper {
    public MerchantDTO getMerchantById(long merchantId);
    public boolean setMerchantMaster(long merchantId,String merchantMasterName, String merchantMasterCode,String merchantMasterImageAddr);
    public boolean addMerchantProofImage(long merchantId,List<String> addrList, int max);
    public boolean getMerchantProofImageByIndex(long merchantId, int max);
    public boolean removeMerchantProofImage(long merchantId,int index);
    public boolean updateMerchantProofImage(long merchantId,String merchantProofImageAddr,int index,int max);
}
