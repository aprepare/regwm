package com.it.regwm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.regwm.common.CustomException;
import com.it.regwm.entity.Category;
import com.it.regwm.entity.Dish;
import com.it.regwm.entity.Setmeal;
import com.it.regwm.mapper.CategoryMapper;
import com.it.regwm.service.CategoryService;
import com.it.regwm.service.DishService;
import com.it.regwm.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishlambdaQueryWrapper=new LambdaQueryWrapper<>();
        dishlambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1=dishService.count(dishlambdaQueryWrapper);
        if (count1>0){
            throw new CustomException("当前分类下关联了菜品，无法删除");
        }

        LambdaQueryWrapper<Setmeal> setmeallambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmeallambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2=setmealService.count(setmeallambdaQueryWrapper);
        if (count2>0){
            throw new CustomException("当前分类下关联了套餐，无法删除");
        }

    }
}
