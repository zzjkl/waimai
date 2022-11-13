package com.zzj.waimai.dto;


import com.zzj.waimai.pojo.Dish;
import com.zzj.waimai.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
