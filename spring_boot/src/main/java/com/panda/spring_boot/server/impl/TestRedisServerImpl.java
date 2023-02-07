package com.panda.spring_boot.server.impl;

import com.panda.spring_boot.server.TestRedisServer;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class TestRedisServerImpl implements TestRedisServer {

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public String testRedis() {

        for (int i = 0; i < 10; i++) {

            int finalI = 100;

            new Thread(() -> {

                System.out.println(finalI);
                RLock lock = redissonClient.getLock(String.valueOf(finalI));
                try {
                    System.out.println("redissonClient: " + redissonClient.getId() + "  thread-id: " + Thread.currentThread().getId());

                    // 1. 取锁时的等待时间
                    // 2. 可持有锁的时间（过期时间）
                    // 3. 时间单位
                    boolean b = lock.tryLock(0, 1000L, TimeUnit.SECONDS);
                    if (b) {
                        System.out.println("加锁成功 " + Thread.currentThread().getId());
                    }else {
                        System.out.println("加锁失败 " + Thread.currentThread().getId());
                    }
                } catch (InterruptedException e) {
                    System.out.println("加锁发生错误");
                }finally {
                    lock.unlock();
                }

            }).start();
        }
        return null;
    }
}
