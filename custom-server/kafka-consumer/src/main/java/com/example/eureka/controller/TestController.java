package com.example.eureka.controller;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LaoGaoChuang
 * @Date : 2019/4/28 15:55
 */
@RestController
public class TestController {
    @Autowired
    private RedisLockRegistry redisLockRegistry;

    @RequestMapping("/testClock")
    public String testClock() throws InterruptedException {
        String key = "redis-clock-key";
        //
        Lock lock = redisLockRegistry.obtain(key);
        //尝试得到锁,尝试10秒
        if (lock.tryLock(10, TimeUnit.SECONDS)) {
            try {
                //得到锁的操作
            } finally {
                try {
                    lock.unlock();
                } catch (IllegalStateException e) {
                    //锁已经失效回滚锁了的资源
                    throw new RuntimeException("操作时间超时,锁已经失效");
                }
            }
        } else {
            // 没有得到锁
        }
        return "1";
    }
}
