package com.seek.food.admin.Controller;

import com.seek.food.admin.Enum.RequestPathEnum;
import com.seek.food.dto.Admin.SuggestionDTO;
import com.seek.food.dto.Common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping(RequestPathEnum.Admin_Suggestion)
@RestController
public class SuggestionController {

    //插入建议
    @PostMapping
    public Result<Void> insertSuggestion(String description, MultipartFile file){
        return Result.success();
    }

    //批量查询建议
    @GetMapping
    public Result<List<SuggestionDTO>> getSuggestionList(int start, int number){
        return Result.success();
    }

    //确认评阅建议
    @PutMapping
    public Result<Void> ackSuggestion(long suggestionId){
        return Result.success();
    }
}
