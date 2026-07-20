package com.seek.food.fund.Service;

public interface FundService {
    public void recharge(int rechargeAmount,String description);
    public void withdraw(int withdrawAmount,String description);
}
