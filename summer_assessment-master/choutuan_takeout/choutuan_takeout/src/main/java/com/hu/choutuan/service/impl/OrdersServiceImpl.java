package com.hu.choutuan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.choutuan.entity.*;
import com.hu.choutuan.mapper.OrdersMapper;
import com.hu.choutuan.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param orders
     */
    @Transactional
    public void submit(Orders orders, HttpSession session) {

        String userId = (String) session.getAttribute("userId");//获得当前用户id

        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);//根据用户ID查询购物车
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);//返回所有符合条件的记录

        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getEmail,userId);//根据邮箱号获取数据
        User user = userService.getOne(queryWrapper2);

        //获取默认地址
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault,1).and(queryWrapper1 -> queryWrapper1.eq(AddressBook::getUserId,userId));//查询默认地址
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        long orderId = IdWorker.getId();//通过雪花算法获得一个订单id

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            /* ***strem() 是将shoppingCarts里面的数据变成流的形式，然后将每个shoppingCarts中的
            每个值传入到map中的方法中去并通过collect(Collectors.toList())构建成新的list*** */
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);//上面生成的唯一Id
            orderDetail.setNumber(item.getNumber());//订单数量
            orderDetail.setName(item.getName());//下单姓名
            orderDetail.setDishName(item.getDishName());//下单菜品名称
            orderDetail.setAmount(item.getAmount());//下单金额
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());//下单金额总和
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setAddressBookId(orderId);//设置唯一订单id
        orders.setOrderTime(LocalDateTime.now());//设置下单时间
        orders.setCheckoutTime(LocalDateTime.now());//设置付款时间
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);//下单用户
        orders.setNumber(String.valueOf(orderId));//订单号
        orders.setUserName(user.getName());//用户名
        orders.setConsignee(addressBook.getConsignee());//姓名
        orders.setPhone(addressBook.getPhone());//手机号

        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

        this.save(orders);//向订单表插入数据，一条数据

        orderDetailService.saveBatch(orderDetails);//向订单明细表插入数据，多条数据

        shoppingCartService.remove(wrapper);//清空购物车数据
    }

    /**
     * 根据当前userId以及用户名称删除订单
     * session
     */
    public void delete(HttpSession session,String name){
        //先获取当前userId
        String userId = (String) session.getAttribute("userId");

        /* 888 对orders进行操作 888 */

        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getUserId,userId);
        //删除符合条件的记录
        this.remove(queryWrapper);

        /* 888 对ordersDetail进行操作 888 */

        //条件构造器
        LambdaQueryWrapper<OrderDetail> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(OrderDetail::getName,name);
        //删除符合条件的记录
        orderDetailService.remove(queryWrapper1);

    }

}
