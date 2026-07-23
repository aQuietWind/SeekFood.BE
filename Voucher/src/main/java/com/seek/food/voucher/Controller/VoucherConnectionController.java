package com.seek.food.voucher.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.dto.Voucher.VoucherConnectionDTO;
import com.seek.food.voucher.Enum.RequestPathEnum;
import com.seek.food.voucher.Service.VoucherConnectionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(RequestPathEnum.Voucher_Connection)
@RestController
public class VoucherConnectionController {


    private final VoucherConnectionService voucherConnectionService;

    public VoucherConnectionController(VoucherConnectionService voucherConnectionService) {
        this.voucherConnectionService = voucherConnectionService;
    }

    //用户批量获取该持有关系
    @GetMapping(RequestPathEnum.Voucher_Connection_Get_Simple)
    public Result<List<VoucherConnectionDTO>> getSimple(int start,int need) {
        return Result.success(voucherConnectionService.getSimple(start, need));
    }

    //用户批量获取有效持有关系
    @GetMapping(RequestPathEnum.Voucher_Connection_Get_Simple_Effective)
    public Result<List<VoucherConnectionDTO>> getSimpleEffective(int start,int need) {
        return Result.success(voucherConnectionService.getSimpleEffective(start, need));
    }

    //用户获取持有关系的详细信息
    @GetMapping(RequestPathEnum.Voucher_Connection_Get_Detail)
    public Result<VoucherConnectionDTO> getDetail(long connectionId) {
        return Result.success(voucherConnectionService.getDetail(connectionId));
    }

    //其他模块通过Feign调用获取判断是否存在持有关系
    @GetMapping(RequestPathEnum.Voucher_Connection_Exist)
    public Result<Boolean> exist(long promotionId) {
        return Result.success(voucherConnectionService.exist(promotionId));
    }

    //其他模块通过Feign调用获取判断是否存在持有关系
    @GetMapping(RequestPathEnum.Voucher_Connection_Lock)
    public Result<Boolean> lock(long connectionId,long orderId) {
        return Result.success(voucherConnectionService.lock(connectionId,orderId));
    }
















}
