package com.seek.food.meal.Mapper;

import com.seek.food.dto.Meal.MealDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MealMapper {
    public void insertMeal(String mealName,double mealPrice,String mealContent,long merchantId);
    public List<MealDTO> getSimple(long merchantId, int start, int need);
    public List<MealDTO> getSimpleByType(long merchantId,int type,int start,int need);
    public MealDTO getDetail(long mealId,long merchantId);
    public List<MealDTO> merchantGetSimple(int start,int need,long merchantId);
    public List<MealDTO> merchantGetSimpleByType(int type,int start,int need,long merchantId);
    public MealDTO merchantGetDetail(long mealId,long merchantId);
    public void updateMessage(MealDTO meal);
    public void updateShowImage(long mealId, MultipartFile file,long merchantId);
    public void updatePrice(long mealId,double price,long merchantId);
    public void updateSell(long mealId,long merchantId);
    public void deleteMeal(long mealId,long merchantId);
    public void stopLock(long mealId,long merchantId);
    public void lockMeal(long mealId, long merchantId, LocalDateTime deleteTime,String deleteLetterId);
    public String getShowImageAddr(long mealId,long merchantId);
}
