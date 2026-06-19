package com.seek.food.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("1")
public class testRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping
    public String testRedis(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }
    @PostMapping
    public void testRedisPost(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

}
