package com.seek.food.fund.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.fund.Enum.RequestPathEnum;
import com.seek.food.fund.Service.FundService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(RequestPathEnum.Fund)
public class FundOrderRefundRecordController {
    private final FundService fundService;
    public FundOrderRefundRecordController(FundService fundService) {
        this.fundService = fundService;
    }

    //充值余额
    @PutMapping(RequestPathEnum.Fund_Recharge)
    public Result<Void> recharge(int rechargeAmount,String description) {
        fundService.recharge(rechargeAmount,description);
        return Result.success();
    }

    //提现余额
    @PutMapping(RequestPathEnum.Fund_Withdraw)
    public Result<Void> withdraw(int withdrawAmount,String description) {
        fundService.withdraw(withdrawAmount,description);
        return Result.success();
    }





}