package com.seek.food.merchant.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.dto.Merchant.MerchantEsDTO;
import com.seek.food.merchant.Enum.RequestPathEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RequestPathEnum.Search)
public class SearchController {

    @GetMapping(RequestPathEnum.Search_Feed)
    public Result<List<MerchantEsDTO>> feedMerchant(){
        return Result.success();
    }
}
