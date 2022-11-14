package com.zzj.waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzj.waimai.dto.SetmealDto;
import com.zzj.waimai.pojo.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public SetmealDto getSetmealData(Long id);

    public void removeWithDish(List<Long> ids);
}
