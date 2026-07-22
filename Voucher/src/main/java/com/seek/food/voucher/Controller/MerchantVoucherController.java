package com.seek.food.voucher.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.dto.Voucher.MerchantVoucherDTO;
import com.seek.food.voucher.Enum.RequestPathEnum;
import com.seek.food.voucher.Service.MerchantVoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(RequestPathEnum.Merchant_Voucher)
public class MerchantVoucherController {

    private final MerchantVoucherService merchantVoucherService;

    public MerchantVoucherController(MerchantVoucherService merchantVoucherService) {
        this.merchantVoucherService = merchantVoucherService;
    }

    @PostMapping
    public Result<Void> insertMerchantVoucher(@RequestBody MerchantVoucherDTO voucher) {
        merchantVoucherService
        return Result.success();
    }
}
