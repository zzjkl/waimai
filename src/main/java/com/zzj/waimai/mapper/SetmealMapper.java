package com.zzj.waimai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzj.waimai.dto.SetmealDto;
import com.zzj.waimai.pojo.Setmeal;
import com.zzj.waimai.pojo.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper

public interface SetmealMapper extends BaseMapper<Setmeal> {
    List<SetmealDish> listSetmeal(int page, int pageSize, String name);

    SetmealDto getSetmealData(Long id);
}
