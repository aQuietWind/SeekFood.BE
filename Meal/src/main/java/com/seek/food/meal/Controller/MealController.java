package com.seek.food.meal.Controller;

import com.seek.food.meal.Service.MealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MealController {
    private final MealService mealService;
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }
}
