package com.it.regwm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.regwm.common.R;
import com.it.regwm.entity.Category;
import com.it.regwm.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
      @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");

    }
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){

        Page<Category> pageInfo=new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Category::getSort);
        //进行分页查询
        categoryService.page(pageInfo,lambdaQueryWrapper);
        return R.success(pageInfo);
    }
    @DeleteMapping
    public R<String> delete(Long ids){
          log.info("删除分类，id为：{}",ids);
          categoryService.remove(ids);

          return R.success("删除成功");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){
          log.info("修改分类信息，{}",category);
          categoryService.updateById(category);
          return R.success("修改分类信息成功");
    }
}
