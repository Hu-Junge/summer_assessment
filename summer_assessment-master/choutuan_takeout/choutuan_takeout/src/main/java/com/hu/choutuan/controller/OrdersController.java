package com.hu.choutuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.choutuan.common.R;
import com.hu.choutuan.entity.OrderDetail;
import com.hu.choutuan.entity.Orders;
import com.hu.choutuan.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 订单管理----用户端
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders, HttpSession session){
        ordersService.submit(orders,session);
        log.info("下单成功");
        return R.success("下单成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param userId
     * @return
     */
    @GetMapping("/search")
    public R<Page> orderDetail(int page, int pageSize, String userId){
        Page pageInfo = new Page(page, pageSize);//首先执行分页构造

        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();//接着执行条件构造

        if ( userId != null )//查询条件
            queryWrapper.like(Orders::getUserId,userId);//查询getName是否等于name

        ordersService.page(pageInfo, queryWrapper);//执行分页

        log.info("分页查询功能正常");

        return R.success(pageInfo);
    }

    /**
     * 订单细节展示
     */
    public void detail(){

    }

    /**
     * 删除订单---根据用户名称
     * session
     */
    @DeleteMapping("/delete")
    public R<String> delete(HttpSession session,String name){
        ordersService.delete(session,name);
        return R.success("删除成功");
    }
}
