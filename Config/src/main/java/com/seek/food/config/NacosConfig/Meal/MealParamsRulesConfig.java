package com.seek.food.config.NacosConfig.Meal;

import com.seek.food.config.Enum.ConfigKeyEnum;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Meal_Params_Rules_Config)
public class MealParamsRulesConfig {
    private int mealNameMax;
    private int mealDescriptionMax;
    private int mealContextMax;
    private int mealTypeMax;
    private int mealLockDay;
    private String mealShowImageDest;

    public void mealNameCheck(String mealName) {
        if (mealName == null || mealName.isBlank() || mealName.length()>mealNameMax) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }

    public void mealDescriptionCheck(String mealDescription) {
        if (mealDescription!=null&&(mealDescription.isBlank()||mealDescription.length()>mealDescriptionMax) )throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }

    public void mealContextCheck(String mealContext) {
        if (mealContext!=null&&(mealContext.isBlank()||mealContext.length()>mealContextMax)) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }

    public void mealTypeCheck(int mealType) {
        if (mealType<0||mealType>mealTypeMax) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }

    public void mealPriceCheck(Double price) {
        if (price==null||price.isNaN()||price<=0) throw new BizException(ErrorCodeEnum.PARAM_ERROR);
    }
}
