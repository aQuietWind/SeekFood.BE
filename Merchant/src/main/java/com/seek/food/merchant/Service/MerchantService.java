package com.seek.food.merchant.Service;

import com.seek.food.dto.Merchant.MerchantDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MerchantService {
    public MerchantDTO getMerchantDetail(long merchantId);
    public MerchantDTO getMerchantSelf();
    public void updateMerchantMaster(String merchantMasterName, String merchantMasterCode
            , @RequestBody MultipartFile masterImage);
    public void addMerchantProofImage(List<MultipartFile> merchantProofImages);
    public void removeMerchantProofImage(int index);
    public void updateMerchantProofImage(MultipartFile merchantProofImage,int index);
}
