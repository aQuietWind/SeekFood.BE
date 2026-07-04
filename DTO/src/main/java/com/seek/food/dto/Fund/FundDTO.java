package com.seek.food.dto.Fund;

import java.time.LocalDateTime;

public class FundDTO {
    private Long fundId;
    private Long accountId;
    private LocalDateTime createTime;
    private Long fundAmount;
    private Boolean delete;

    public FundDTO() {
    }

    public FundDTO(Long fundId, Long accountId, LocalDateTime createTime, Long fundAmount, Boolean delete) {
        this.fundId = fundId;
        this.accountId = accountId;
        this.createTime = createTime;
        this.fundAmount = fundAmount;
        this.delete = delete;
    }

    /**
     * 获取
     * @return fundId
     */
    public Long getFundId() {
        return fundId;
    }

    /**
     * 设置
     * @param fundId
     */
    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }

    /**
     * 获取
     * @return accountId
     */
    public Long getAccountId() {
        return accountId;
    }

    /**
     * 设置
     * @param accountId
     */
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取
     * @return createTime
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 设置
     * @param createTime
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取
     * @return fundAmount
     */
    public Long getFundAmount() {
        return fundAmount;
    }

    /**
     * 设置
     * @param fundAmount
     */
    public void setFundAmount(Long fundAmount) {
        this.fundAmount = fundAmount;
    }

    /**
     * 获取
     * @return delete
     */
    public Boolean getDelete() {
        return delete;
    }

    /**
     * 设置
     * @param delete
     */
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String toString() {
        return "FundDTO{fundId = " + fundId + ", accountId = " + accountId + ", createTime = " + createTime + ", fundAmount = " + fundAmount + ", delete = " + delete + "}";
    }
}
