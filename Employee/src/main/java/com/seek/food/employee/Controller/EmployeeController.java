package com.seek.food.employee.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.employee.Enum.RequestPathEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestPathEnum.Employee)
public class EmployeeController {

    @PostMapping
    public Result<Void> insertEmployee() {
        return Result.success();
    }
}
