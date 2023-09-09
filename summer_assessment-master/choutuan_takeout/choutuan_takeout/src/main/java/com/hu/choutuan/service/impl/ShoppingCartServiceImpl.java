package com.hu.choutuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.choutuan.entity.ShoppingCart;
import com.hu.choutuan.mapper.ShoppingCartMapper;
import com.hu.choutuan.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
