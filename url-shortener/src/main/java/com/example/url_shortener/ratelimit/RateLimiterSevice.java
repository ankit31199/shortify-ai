package com.example.url_shortener.ratelimit;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RateLimiterSevice {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final int LIMIT = 5;

    public Boolean isAllowed(String key)
    {
        Long count= redisTemplate.opsForValue().increment(key);
        if(count == 1)
        {
            redisTemplate.expire(key, 1, TimeUnit.MINUTES);
        }

        return count<= LIMIT;
    }
}
