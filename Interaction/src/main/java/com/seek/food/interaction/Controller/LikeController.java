package com.seek.food.interaction.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.interaction.Enum.RequestPathEnum;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RequestPathEnum.Interaction_Like)
public class LikeController {

    @PutMapping(RequestPathEnum.Interaction_Like_Merchant)
    public Result<Boolean> likeMerchant(long merchantId,boolean value) {
        return Result.success();
    }
}
