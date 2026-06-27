package com.seek.food.util.CommonUtil;

import java.util.Random;

public class RandomOPT {
    public static String generateOPT(int n){
        StringBuilder opt = new StringBuilder();
        for (int i = 0; i < n; i++) {
            //添加一位随机数字
            opt.append(new Random().nextInt(10));
        }
        return opt.toString();
    }
}
