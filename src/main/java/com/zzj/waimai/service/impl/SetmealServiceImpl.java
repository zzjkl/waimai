package com.zzj.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzj.waimai.mapper.SetmealMapper;
import com.zzj.waimai.pojo.Setmeal;
import com.zzj.waimai.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
