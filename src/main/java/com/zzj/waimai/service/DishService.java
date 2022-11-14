package com.zzj.waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzj.waimai.dto.DishDto;
import com.zzj.waimai.pojo.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish flavor
    DishDto getByIdWithFlavor(Long id);
    //根据id查询菜品信息和对应的口味信息
     void addDishWithFlavor(DishDto dishDto);
    //更新菜品信息，同时更新对应的口味信息
     void updateDishWithFlavor(DishDto dishDto);
}
