package com.seek.food.dto.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {
    private Long userId;
    private String username;
    private String phoneNumber;
    private String password;
    private Byte sex;
    private String headerImageAddr;
    private LocalDate birthday;
    private LocalDateTime createTime;
    private boolean delete;
    private Integer orderAmount;

    public UserDTO() {
    }

    public UserDTO(Long userId, String username, String phoneNumber, String password, Byte sex, String headerImageAddr, LocalDate birthday, LocalDateTime createTime, boolean delete, Integer orderAmount) {
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.sex = sex;
        this.headerImageAddr = headerImageAddr;
        this.birthday = birthday;
        this.createTime = createTime;
        this.delete = delete;
        this.orderAmount = orderAmount;
    }

    /**
     * 获取
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 设置
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return sex
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置
     * @param sex
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * 获取
     * @return headerImageAddr
     */
    public String getHeaderImageAddr() {
        return headerImageAddr;
    }

    /**
     * 设置
     * @param headerImageAddr
     */
    public void setHeaderImageAddr(String headerImageAddr) {
        this.headerImageAddr = headerImageAddr;
    }

    /**
     * 获取
     * @return birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * 设置
     * @param birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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
     * @return delete
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     * 设置
     * @param delete
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     * 获取
     * @return orderAmount
     */
    public Integer getOrderAmount() {
        return orderAmount;
    }

    /**
     * 设置
     * @param orderAmount
     */
    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String toString() {
        return "UserDTO{userId = " + userId + ", username = " + username + ", phoneNumber = " + phoneNumber + ", password = " + password + ", sex = " + sex + ", headerImageAddr = " + headerImageAddr + ", birthday = " + birthday + ", createTime = " + createTime + ", delete = " + delete + ", orderAmount = " + orderAmount + "}";
    }
}