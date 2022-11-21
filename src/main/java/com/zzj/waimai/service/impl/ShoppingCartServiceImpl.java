package com.zzj.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzj.waimai.mapper.ShoppingCartMapper;
import com.zzj.waimai.pojo.ShoppingCart;
import com.zzj.waimai.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>implements ShoppingCartService {
}
