package com.hu.choutuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.choutuan.common.R;
import com.hu.choutuan.entity.Employee;
import com.hu.choutuan.entity.OrderDetail;
import com.hu.choutuan.entity.Orders;
import com.hu.choutuan.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 订单管理----管理端
 */
@Slf4j
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 订单分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping
    public R<Page> orderDetail(int page, int pageSize,String name){
        Page pageInfo = new Page(page, pageSize);//首先执行分页构造

        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();//接着执行条件构造

        if ( name != null )//查询条件
            queryWrapper.like(OrderDetail::getName,name);//查询getName是否等于name

        orderDetailService.page(pageInfo, queryWrapper);//执行分页

        log.info("分页查询功能正常");

        return R.success(pageInfo);
    }

    /**
     * 根据usreId查询订单
     * @param userId
     * @return
     */
    @PostMapping("/search")
    public R<String> search(String userId){
        orderDetailService.search(userId);
        log.info("根据userId查询成功");
        return R.success("查询成功");
    }

}
