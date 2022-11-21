package com.zzj.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzj.waimai.mapper.OrderDetailMapper;
import com.zzj.waimai.pojo.OrderDetail;
import com.zzj.waimai.service.OrderDetailService;

import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>implements OrderDetailService {
}
