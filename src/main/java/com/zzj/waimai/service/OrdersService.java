package com.zzj.waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzj.waimai.pojo.Orders;

public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
