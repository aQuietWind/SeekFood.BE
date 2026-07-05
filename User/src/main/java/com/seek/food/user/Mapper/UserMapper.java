package com.seek.food.user.Mapper;

import com.seek.food.dto.User.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    public UserDTO getUserDetailMessage(long userId);
    public boolean updateUserPassword(String phoneNumber,String newPassword);
    public boolean updateUserHeader(long userId, String path);
}
