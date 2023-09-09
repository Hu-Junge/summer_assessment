package com.hu.choutuan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hu.choutuan.common.R;
import com.hu.choutuan.entity.OrderDetail;
import com.hu.choutuan.entity.Orders;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrderDetailService extends IService<OrderDetail> {
    /**
     * 根据userId查询订单
     * @param userId
     */
    public R<List<Orders>> search(String userId);
}
