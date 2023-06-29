package com.it.regwm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.regwm.entity.Dish;
import com.it.regwm.mapper.DishMapper;
import com.it.regwm.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

}
