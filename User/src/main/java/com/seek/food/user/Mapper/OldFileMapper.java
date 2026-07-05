package com.seek.food.user.Mapper;

import com.seek.food.dto.User.OldFileDTO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface OldFileMapper {
    public OldFileDTO getOldFileByUserId(long userId);
    public boolean deleteFile(String addr);
}
