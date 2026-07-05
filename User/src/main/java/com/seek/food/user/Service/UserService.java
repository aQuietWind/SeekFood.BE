package com.seek.food.user.Service;

import com.seek.food.dto.Common.Result;
import com.seek.food.dto.User.UserDTO;

public interface UserService {
    public UserDTO getUserDetailMessage(long userId);
    public UserDTO getUserSelfMessage();
    public String updateUserPasswordGetOpt(String phoneNumber);
    public void updateUserPassword(String phoneNumber, String newPassword,String opt);
}
