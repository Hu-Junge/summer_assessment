package com.hu.choutuan.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hu.choutuan.common.R;
import com.hu.choutuan.entity.AddressBook;
import com.hu.choutuan.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public R<AddressBook> save(@RequestBody AddressBook addressBook, HttpSession session) {

        String userId = (String) session.getAttribute("userId");//从session中获取用户ID
        addressBook.setUserId(userId);//增加用户ID
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);//保存记录
        log.info("添加地址成功");
        return R.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook,HttpSession session) {
        //条件构造器
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, (String) session.getAttribute("userId"));//相当于session的ID与数据库中的比对

        wrapper.set(AddressBook::getIsDefault, 0);//设置为普通地址
        addressBookService.update(wrapper);//更新为符合条件的记录

        addressBook.setIsDefault(1);//设置为默认地址
        addressBookService.updateById(addressBook);

        return R.success(addressBook);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        //@PathVariable是URL的占位符
        AddressBook addressBook = addressBookService.getById(id);//根据id查询记录
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("不存在");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R<AddressBook> getDefault(HttpSession session) {
        //条件构造器
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, (String) session.getAttribute("userId"));//对ID进行比对
        queryWrapper.eq(AddressBook::getIsDefault, 1);//获取默认的地址记录

        AddressBook addressBook = addressBookService.getOne(queryWrapper);//保存符合条件的记录

        if (null == addressBook) {
            return R.error("不存在");
        } else {
            return R.success(addressBook);
        }
    }

    /**
     * 查询用户全部地址
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook,HttpSession session) {
        addressBook.setUserId((String) session.getAttribute("userId"));
        log.info("addressBook:{}", addressBook);

        //条件构造器
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBook::getUserId, addressBook.getUserId());

        return R.success(addressBookService.list(queryWrapper));
    }

}
