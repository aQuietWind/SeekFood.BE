package com.seek.food.merchant.Mapper;

import com.seek.food.dto.Merchant.MerchantDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MerchantMapper {
    public MerchantDTO getMerchantById(long merchantId);
    public boolean setMerchantMaster(long merchantId,String merchantMasterName, String merchantMasterCode,String merchantMasterImageAddr);
    public boolean addMerchantProofImage(long merchantId,String addr, int max);
    public String getMerchantProofImageByIndex(long merchantId, int index);
    public boolean removeMerchantProofImage(long merchantId,String oldAddr,int index);
    public boolean replaceMerchantProofImage(long merchantId,String addr,String oldAddr,int index);
    public boolean addShowImage(long merchantId,String addr, int max);
    public String getShowImageByIndex(long merchantId, int index);
    public boolean removeShowImage(long merchantId,String oldAddr,int index);
    public boolean replaceShowImage(long merchantId,String addr,String oldAddr,int index);
}
