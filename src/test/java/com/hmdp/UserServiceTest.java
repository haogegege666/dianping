package com.hmdp;

import cn.hutool.core.util.RandomUtil;
import com.hmdp.entity.User;
import com.hmdp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testFindUserById() {
        // 假设要查询的用户ID为1
        Long userId = 1L;
        
        // 调用用户服务方法查询用户信息
        User user = userService.getById(userId);
        
        // 验证查询结果
        assertNotNull(user);
        System.out.println("用户信息：" + user.toString());
    }
    @Test
    public void saveUser(){
        User user = new User();
        user.setPhone("18833248948");
        user.setNickName("user_"+ RandomUtil.randomString(10));
        userService.save(user);
    }
    @Test
    public void findUser(){


        User user = userService.getById(1010);
        System.out.println(user.getPhone());
    }
}
