package com.zzj.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzj.waimai.mapper.DishMapper;
import com.zzj.waimai.pojo.Dish;
import com.zzj.waimai.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
