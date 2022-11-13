package com.zzj.waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.zzj.waimai.pojo.Category;

public interface CategoryService extends IService<Category> {
    void removeCategory(Long id);
}
