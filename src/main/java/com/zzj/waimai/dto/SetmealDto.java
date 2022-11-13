package com.zzj.waimai.dto;


import com.zzj.waimai.pojo.Setmeal;
import com.zzj.waimai.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
