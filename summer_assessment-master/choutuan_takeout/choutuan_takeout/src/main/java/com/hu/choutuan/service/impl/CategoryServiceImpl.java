package com.hu.choutuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.choutuan.entity.Category;
import com.hu.choutuan.mapper.CategoryMapper;
import com.hu.choutuan.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
}
