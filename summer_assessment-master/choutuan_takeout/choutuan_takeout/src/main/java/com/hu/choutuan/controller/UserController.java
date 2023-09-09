package com.hu.choutuan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hu.choutuan.common.R;
import com.hu.choutuan.entity.User;
import com.hu.choutuan.service.UserService;
import com.hu.choutuan.utils.EmailUtils;
import com.hu.choutuan.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户登录
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     *
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取邮箱号并查看是否为空
        String email = user.getEmail();
        if (email == null) {
            return R.error("邮箱号为空！");
        }

        /* *** 调用工具类 *** */
        String code = String.valueOf(ValidateCodeUtils.generateValidateCode(4));// 随机生成4位验证码
        log.info("验证码为：{}", code);
        EmailUtils.sendAuthCodeEmail(email,code);//发送验证码到邮箱

        session.setAttribute("code", code);// 将验证码保存到session
        session.setAttribute("email", email);//将手机号保存到session

        log.info("验证码发送成功");
        return R.success("验证码发送成功");
    }

    /**
     * 登录功能
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> map, HttpSession session) {

        String email = map.get("email");// 获取邮箱号
        String code = map.get("code");// 获取验证码
        String codeInSession = (String) session.getAttribute("code");// 从session中获取验证码
        String emailInSession = (String) session.getAttribute("email");//从session中获取请求验证码的邮箱号

        // 进行邮箱号和验证码比对
        if (codeInSession == null || emailInSession== null || !codeInSession.equals(code) || !emailInSession.equals(email)) {
            return R.error("验证码错误");
        }

        // 条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper .eq(User::getEmail, email);//进行邮箱号的比对(与数据库中的邮箱号进行比对)

        User user = userService.getOne(queryWrapper );//获取最终符合条件的记录

        if (user == null) {
        //用户还未注册，自动注册
          user = new User();
          user.setEmail(email);
          userService.save(user);
        }

        session.setAttribute("userId", user.getEmail());//将id保存到session中名为uesrId的对象
        session.setAttribute("user", user.getId());//将id保存到session中名为uesr的对象
        session.setMaxInactiveInterval(0);//设置为session中的值用不失效

        session.removeAttribute("code");//清除验证码

        log.info("登录成功");
        return R.success(user);
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清除session
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("email");
        request.getSession().removeAttribute("code");
        log.info("退出成功");
        return R.success("退出登录成功");
    }

}