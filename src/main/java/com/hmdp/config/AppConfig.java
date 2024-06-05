package com.hmdp.config;

import com.hmdp.utils.RedisIdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RedisIdWorker redisIdWorker(StringRedisTemplate stringRedisTemplate) {
        return new RedisIdWorker(stringRedisTemplate);
    }
}
