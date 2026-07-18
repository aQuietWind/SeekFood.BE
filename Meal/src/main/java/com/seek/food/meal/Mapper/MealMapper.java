package com.seek.food.meal.Mapper;

import com.seek.food.dto.Meal.MealDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MealMapper {
    public void insertMeal(String mealName,double mealPrice,String mealContent,long merchantId);
    public List<MealDTO> getSimple(long merchantId, int start, int need);
    public List<MealDTO> getSimpleByType(long merchantId,int type,int start,int need);
    public MealDTO getDetail(long mealId);
    public List<MealDTO> merchantGetSimple(int start,int need,long merchantId);
    public List<MealDTO> merchantGetSimpleByType(int type,int start,int need,long merchantId);
    public MealDTO merchantGetDetail(long mealId,long merchantId);
    public boolean updateMessage(MealDTO meal);
    public boolean updateShowImage(long mealId, String addr,String oldAddr,long merchantId);
    public boolean updatePrice(long mealId,double price,long merchantId);
    public boolean updateSell(long mealId,long merchantId);
    public boolean deleteMeal(long mealId,String letterId);
    public boolean stopLock(long mealId,long merchantId);
    public boolean lockMeal(long mealId, long merchantId, LocalDateTime deleteTime,String deleteLetterId);
    public String getShowImageAddr(long mealId,long merchantId);
}
