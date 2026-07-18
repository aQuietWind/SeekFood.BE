package com.seek.food.meal.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.meal.Enum.RequestPathEnum;
import com.seek.food.meal.Service.MealService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestPathEnum.Meal)
public class MealController {
    private final MealService mealService;
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping(RequestPathEnum.Meal_Insert)
    public Result<Void> insertMeal(String mealName,double mealPrice,String mealContent) {

        return Result.success();
    }


}
