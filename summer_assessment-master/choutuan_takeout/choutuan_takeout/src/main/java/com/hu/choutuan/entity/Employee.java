//员工实体类----和Employee映射
package com.hu.choutuan.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;//serialVersionUID 相当于java类的身份证。主要用于版本控制

    private int id;//主键

    private String username;//用户名

    private String name;//姓名

    private String password;//密码

    private String phone;//电话

    private String sex;//性别

    private String idNumber;//身份证----驼峰命名
}
