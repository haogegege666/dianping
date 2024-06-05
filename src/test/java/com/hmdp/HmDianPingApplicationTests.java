package com.hmdp;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.entity.Shop;
import com.hmdp.service.IShopService;
import com.hmdp.service.impl.ShopServiceImpl;
import com.hmdp.utils.RedisIdWorker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class HmDianPingApplicationTests{

    @Resource
    private ShopServiceImpl shopService;
    @Resource
    private RedisIdWorker redisIdWorker;
    private ExecutorService es = Executors.newFixedThreadPool(50);
    @Test
    public void testIdWorker() throws InterruptedException {
        System.out.println("nihao");
        CountDownLatch latch = new CountDownLatch(100);

        Runnable task = () -> {
            for (int i = 0; i < 100; i++) {
                long id = redisIdWorker.nextId("order");
                System.out.println("id = " + id);
            }
            latch.countDown();
        };
        System.out.println("nihao1");
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            es.submit(task);
        }
        System.out.println("nihao2");
        latch.await();
        System.out.println("nihao3");
        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - begin));
    }



    @Test
    public void findshop() {
        Long id = 15L;


        Shop shop = shopService.getById(id);
        System.out.println(shop.getName());
    }



}
