package com.zzj.waimai.dto;

import com.zzj.waimai.pojo.User;
import lombok.Data;

@Data
public class UserDto extends User {
    private String code;
}
