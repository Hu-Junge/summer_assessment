package com.hu.choutuan.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 地址簿
 */
@Data
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String userId;//用户id

    private String consignee;//收货人

    private String phone;//手机号

    private String sex;//性别 0 女 1 男

    private String provinceName;//省级名称

    private String cityName;//市级名称

    private String districtName;//区级名称

    private String detail;//详细地址

    private int isDefault;//设置默认地址，1为默认

}
