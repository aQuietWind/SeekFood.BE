package com.seek.food.food.comment.Service.Impl;

import com.seek.food.dto.Comment.FirstCommentDTO;
import com.seek.food.food.comment.Service.FirstCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RefreshScope
public class FirstCommentServiceImpl implements FirstCommentService {


    //插入一级评论
    @Override
    public void insertComment(String description, long orderId, MultipartFile file){

    }

    //批量获取简易信息
    @Override
    public List<FirstCommentDTO> getSimple(long merchantId, int start, int need){

    }

    //获取某一级评论的详细信息
    @Override
    public FirstCommentDTO getDetail(long commentId){

    }

    //删除评论
    @Override
    public void deleteComment(long commentId){

    }
}
