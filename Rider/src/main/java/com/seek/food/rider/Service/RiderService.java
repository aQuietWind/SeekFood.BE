package com.seek.food.rider.Service;

import org.springframework.web.multipart.MultipartFile;

public interface RiderService {
    public void updatePersonImage(MultipartFile file);
    public void updatePassword(String phoneNumber, String password, String opt);
    public void getDetail(long riderId);
    public void getSelf();
    public String getDeleteOpt();
    public void delete(String opt);
    public String getUpdatePasswordOpt();
}
