package com.seek.food.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class testNacos {
    @Value("${biz.upload-path}")
    private String uploadPath;

    @Value("${biz.page-size}")
    private Integer pageSize;

    public String printConfig() {
        System.out.println(uploadPath + "|" + pageSize);
        return uploadPath;
    }
}
