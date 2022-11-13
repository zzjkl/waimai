package com.zzj.waimai.dto;


import com.zzj.waimai.pojo.OrderDetail;
import com.zzj.waimai.pojo.Orders;
import lombok.Data;

import java.util.List;


@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
