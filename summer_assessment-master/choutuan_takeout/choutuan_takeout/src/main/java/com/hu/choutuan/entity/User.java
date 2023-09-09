package com.hu.choutuan.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
/**
 * 用户信息
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String name;//姓名

    private String userName;//用户名

    private String email;//邮箱号

    private String sex;//性别 0 女 1 男

}
