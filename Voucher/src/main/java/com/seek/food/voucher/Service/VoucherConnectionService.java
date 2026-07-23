package com.seek.food.voucher.Service;

import com.seek.food.dto.Voucher.VoucherConnectionDTO;

import java.util.List;

public interface VoucherConnectionService {
    public List<VoucherConnectionDTO> getSimple(int start, int need);
    public List<VoucherConnectionDTO> getSimpleEffective(int start, int need);
    public VoucherConnectionDTO getDetail(long connectionId);
    public boolean exist(long userId,long promotionId);
}
