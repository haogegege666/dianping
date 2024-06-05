package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result queryById(Long id) {
        String shopJson = stringRedisTemplate.opsForValue().get("cache:shop"+id);
        if(StrUtil.isNotBlank(shopJson)){
            Shop shop = JSONUtil.toBean(shopJson, Shop.class);
            return Result.ok(shop);
        }
        Shop shop = getById(id);
        if(shop==null){
            return Result.fail("商铺不存在");
        }
        log.debug("店铺是 {}");
        log.debug(shop.getName());

        stringRedisTemplate.opsForValue().set("cache:shop"+id, JSONUtil.toJsonStr(shop), 30, TimeUnit.MINUTES);
        return Result.ok(shop);
    }

    @Override
    @Transactional
    public Result update(Shop shop) {
        Long id = shop.getId();
        if(id==null){
            return Result.fail("商铺id不存在");
        }
        updateById(shop);

        stringRedisTemplate.delete("cache:shop"+id);
        return Result.ok();
    }
}
