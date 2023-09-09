package com.hu.choutuan.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车
 */
@Data
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;//姓名

    private String userId;//用户id

    private String dishName;//菜品名称

    private Integer number;//数量

    private BigDecimal amount;//金额
}
