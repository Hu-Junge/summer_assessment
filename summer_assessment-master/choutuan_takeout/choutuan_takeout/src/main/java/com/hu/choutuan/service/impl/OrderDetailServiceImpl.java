package com.hu.choutuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.choutuan.common.R;
import com.hu.choutuan.entity.OrderDetail;
import com.hu.choutuan.entity.Orders;
import com.hu.choutuan.entity.ShoppingCart;
import com.hu.choutuan.mapper.OrderDetailMapper;
import com.hu.choutuan.service.OrderDetailService;
import com.hu.choutuan.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService{

    @Autowired
    private OrdersService ordersService;

    /**
     * 根据userId查询订单
     * @param userId
     */
    public R<List<Orders>> search(String userId){

        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId,userId);//将userId设置为查找条件
        List<Orders> list = ordersService.list(queryWrapper);//返回符合条件的记录

        return R.success(list);
    }
}
