package com.hu.choutuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hu.choutuan.common.R;
import com.hu.choutuan.entity.ShoppingCart;
import com.hu.choutuan.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 购物车
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart, HttpSession session){

        /* ***当点击下单按钮或者增加按钮时*** */

        String userId = (String) session.getAttribute("userId");//获取用户id
        shoppingCart.setUserId(userId);//设置用户id

        String dishName = shoppingCart.getDishName();//获取菜品

        //条件构造器
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userId);//判断用户Id
        if( dishName != null ){
            queryWrapper.eq(ShoppingCart::getDishName,dishName);//判断菜品Id
        }

        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);//获取符合条件的记录

        //若记录不为空
        if(cartServiceOne != null){
            Integer number = cartServiceOne.getNumber();//获取单个菜品下单数量
            cartServiceOne.setNumber(number + 1);//加1
            shoppingCartService.updateById(cartServiceOne);
        }else{
            shoppingCart.setNumber(1);//默认为1
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        log.info("添加成功");
        return R.success(cartServiceOne);
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(HttpSession session){

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,session.getAttribute("userId"));//通过用户ID筛选符合条件的记录
        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);//所有记录组成的集合

        log.info("查看功能正常");
        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(HttpSession session){

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,session.getAttribute("userId"));//通过用户ID筛选符合条件的记录

        shoppingCartService.remove(queryWrapper);//清除所有符合条件的记录

        log.info("已清空");
        return R.success("已清空");
    }
}
