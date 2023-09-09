package com.hu.choutuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.choutuan.entity.Orders;

import javax.servlet.http.HttpSession;

public interface OrdersService extends IService<Orders> {
    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders, HttpSession session);

    /**
     * 根据当前登录userId以及用户名称删除订单
     * @param session
     */
    public void delete(HttpSession session,String name);
}
