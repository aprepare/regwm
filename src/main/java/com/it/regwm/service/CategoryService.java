package com.it.regwm.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.it.regwm.entity.Category;


public interface CategoryService extends IService<Category> {
        void remove(Long id);
}
