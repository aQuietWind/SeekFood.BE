package com.seek.food.merchant.Service;

import com.seek.food.dto.Merchant.MerchantDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MerchantService {
    public MerchantDTO getMerchantDetail(long merchantId);
    public MerchantDTO getMerchantSelf();
    public void setMerchantMaster(String masterName, String masterCode, MultipartFile masterImage);
    public void addMerchantProofImage(MultipartFile image);
    public void removeMerchantProofImage(int index);
    public void replaceMerchantProofImage(MultipartFile image,int index);
    public void addShowImage(MultipartFile image);
    public void removeShowImage(int index);
    public void replaceShowImage(MultipartFile image,int index);
    public void updateHomeImage(MultipartFile image);
    public void updateMerchantMessage(MerchantDTO merchant);
    public String getUpdatePasswordOpt();
    public void updatePassword(String newPassword, String opt);
}
