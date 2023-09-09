package com.hu.choutuan.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细
 */
@Data
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;//姓名

    private Long orderId;//订单id

    private String dishName;//菜品名称

    private Integer number;//数量

    private BigDecimal amount;//金额
}
