package com.hu.choutuan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.choutuan.common.R;
import com.hu.choutuan.entity.Category;
import com.hu.choutuan.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param category
     * @return
     */
    @PostMapping("/add")
    public R<String> save(@RequestBody Category category){
        //@RequestBody接收json形式的数据
        categoryService.save(category);//保存
        log.info("菜品添加功能正常");
        return R.success("添加成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        log.info("启动分页查询");

        Page<Category> pageInfo = new Page<>(page, pageSize);//分页

        LambdaQueryWrapper<Category> queryWrapper =  new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getId);//添加条件 按照ID升序
        log.info("分页查询正常");
        categoryService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public R<String> delete(int id){
        categoryService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 修改信息
     * @param category
     * @return
     */
    @PutMapping("/modify")
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /**
     * 根据条件分类查询
     * @param category
     * @return
     */
    @GetMapping("/search")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType()!=null, Category::getType,category.getType());//相当于条件是数据库中的Type等于你输入的Type

        queryWrapper.orderByAsc(Category::getId);//根据ID升序

        List<Category> list = categoryService.list(queryWrapper);//所有符合条件的记录的集合
        log.info("查询成功");
        return R.success(list);
    }
}
