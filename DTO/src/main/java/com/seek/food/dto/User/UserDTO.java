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
    private Byte isDelete;
    private Integer shopAmount;

    // 无参构造
    public UserDTO(){}

    public UserDTO(Long userId, String username, String phoneNumber, String password, Byte sex, String headerImageAddr, LocalDate birthday, LocalDateTime createTime, Byte isDelete, Integer shopAmount) {
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.sex = sex;
        this.headerImageAddr = headerImageAddr;
        this.birthday = birthday;
        this.createTime = createTime;
        this.isDelete = isDelete;
        this.shopAmount = shopAmount;
    }


    // 全套get/set、toString 不变
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Byte getSex() { return sex; }
    public void setSex(Byte sex) { this.sex = sex; }

    public String getHeaderImageAddr() { return headerImageAddr; }
    public void setHeaderImageAddr(String headerImageAddr) { this.headerImageAddr = headerImageAddr; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public Byte getIsDelete() { return isDelete; }
    public void setIsDelete(Byte isDelete) { this.isDelete = isDelete; }

    public Integer getShopAmount() { return shopAmount; }
    public void setShopAmount(Integer shopAmount) { this.shopAmount = shopAmount; }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", headerImageAddr='" + headerImageAddr + '\'' +
                ", birthday=" + birthday +
                ", createTime=" + createTime +
                ", isDelete=" + isDelete +
                ", shopAmount=" + shopAmount +
                '}';
    }
}