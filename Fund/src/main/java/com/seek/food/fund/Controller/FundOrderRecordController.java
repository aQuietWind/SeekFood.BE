package com.seek.food.fund.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.dto.Fund.FundOrderRecordDTO;
import com.seek.food.fund.Enum.RequestPathEnum;
import com.seek.food.fund.Service.FundOrderRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(RequestPathEnum.Fund_Order_Record)
public class FundOrderRecordController {

    private final FundOrderRecordService fundOrderRecordService;

    public FundOrderRecordController(FundOrderRecordService fundOrderRecordService) {
        this.fundOrderRecordService = fundOrderRecordService;
    }

    @GetMapping(RequestPathEnum.Fund_Order_Record_Get_Simple)
    public Result<List<FundOrderRecordDTO>> getSimple(int start ,int need){
        return Result.success(fundOrderRecordService.getSimple(start, need));
    }

    @GetMapping(RequestPathEnum.Fund_Order_Record_Get_Detail)
    public Result<FundOrderRecordDTO> getDetail(long recordId){
        return Result.success(fundOrderRecordService.getDetail(recordId));
    }

}