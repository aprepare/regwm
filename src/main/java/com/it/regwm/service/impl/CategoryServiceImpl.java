package com.it.regwm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.regwm.entity.Category;
import com.it.regwm.mapper.CategoryMapper;
import com.it.regwm.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
