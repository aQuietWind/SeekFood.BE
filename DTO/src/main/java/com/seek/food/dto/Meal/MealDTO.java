package com.seek.food.dto.Meal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO {
    private Long meal_id;
    private Long merchant_id;
    private String meal_name;
    private Double meal_price;
    private Double meal_last_price;
    private String meal_description;
    private String meal_show_image_addr;
    private String meal_content;
    private Integer meal_type;
    private Integer meal_sales_volume;
    private LocalDateTime next_discount_time;
    private LocalDateTime create_time;
    private Boolean is_sell;
    private Boolean is_lock;
    private Boolean is_delete;
}
