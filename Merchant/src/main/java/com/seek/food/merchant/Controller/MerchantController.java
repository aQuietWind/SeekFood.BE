package com.seek.food.merchant.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.dto.Merchant.MerchantDTO;
import com.seek.food.merchant.Enum.RequestPathEnum;
import com.seek.food.merchant.Service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(RequestPathEnum.Merchant)
public class MerchantController {
    private final MerchantService merchantService;
    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    //查询单个商家详细信息
    @GetMapping(RequestPathEnum.Merchant_Detail)
    public Result<MerchantDTO> getMerchantDetail(long merchantId) {
        return Result.success(merchantService.getMerchantDetail(merchantId));
    }

    //查询自身的详细信息
    @GetMapping(RequestPathEnum.Merchant_Self)
    public Result<MerchantDTO> getMerchantSelf(){
        return Result.success(merchantService.getMerchantSelf());
    }

    //设置店主的信息
    @PutMapping(RequestPathEnum.Merchant_Update_Master)
    public Result<Void> setMerchantMaster(String merchantMasterName, String merchantMasterCode, @RequestBody MultipartFile merchantMasterImage){
        merchantService.setMerchantMaster(merchantMasterName, merchantMasterCode, merchantMasterImage);
        return Result.success();
    }

    //增加商家的营业证明
    @PostMapping(RequestPathEnum.Merchant_Proof)
    public Result<Void> addMerchantProofImage(@RequestBody MultipartFile file){
        merchantService.addMerchantProofImage(file);
        return Result.success();
    }
    //删除商家的营业证明
    @DeleteMapping(RequestPathEnum.Merchant_Proof)
    public Result<Void> removeMerchantProofImage(int index){
        merchantService.removeMerchantProofImage(index);
        return Result.success();
    }
    //替换商家的营业证明
    @PutMapping(RequestPathEnum.Merchant_Proof)
    public Result<Void> replaceMerchantProofImage(@RequestBody MultipartFile file,int index){
        merchantService.replaceMerchantProofImage(file,index);
        return Result.success();
    }

    //增加商家的展示图
    @PostMapping(RequestPathEnum.Merchant_Show)
    public Result<Void> addShowImage(@RequestBody MultipartFile file){
        merchantService.addShowImage(file);
        return Result.success();
    }
    //删除商家的展示图
    @DeleteMapping(RequestPathEnum.Merchant_Show)
    public Result<Void> removeShowImage(int index){
        merchantService.removeShowImage(index);
        return Result.success();
    }
    //替换商家的展示图
    @PutMapping(RequestPathEnum.Merchant_Show)
    public Result<Void> replaceShowImage(@RequestBody MultipartFile file,int index){
        merchantService.replaceShowImage(file,index);
        return Result.success();
    }



}
