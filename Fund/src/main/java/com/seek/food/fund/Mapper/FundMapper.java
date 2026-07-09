package com.seek.food.fund.Mapper;

import com.seek.food.dto.Fund.FundDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FundMapper {
    public boolean insertFund(FundDTO fund);
    public boolean deleteFund(long accountId);
}
