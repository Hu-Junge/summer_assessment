package com.hu.choutuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.choutuan.entity.AddressBook;
import com.hu.choutuan.mapper.AddressBookMapper;
import com.hu.choutuan.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
