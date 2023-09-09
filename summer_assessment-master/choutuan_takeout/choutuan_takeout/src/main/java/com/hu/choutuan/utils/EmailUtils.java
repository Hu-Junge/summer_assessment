package com.hu.choutuan.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.HtmlEmail;

@Slf4j
/**
 * 发送邮箱验证码工具类
 */
public class EmailUtils {
    /**
     * 发送验证码
     * @param email  接收邮箱
     * @param code   验证码
     * @return  void
     * 不能设置安全链接和端口号，会一直报错
     */
    public static void sendAuthCodeEmail(String email,String code) {
        try {
            /* **** try中要执行的语句 **** */
            HtmlEmail mail = new HtmlEmail();

            mail.setHostName("smtp.qq.com");//发送邮件的服务器，QQ为smtp.qq.com

            mail.setCharset("UTF-8");

            mail.setAuthentication("1805824390@qq.com", "mgidlpojozjgehbb");//邮箱号和SMTP服务代码

            mail.setFrom("1805824390@qq.com", "胡峻阁"); //发送邮件的邮箱和发件人

            mail.addTo(email);//要发送的邮箱

            mail.setSubject("登录验证码");//邮件标题
            mail.setMsg("尊敬的用户:你好! 登录验证码为:" + code);//邮件内容

            mail.send();//发送
        } catch (Exception e) {  //Exception e用来保存异常信息
            /* **** 发生异常时执行的语句 **** */
            e.printStackTrace(); //Exception e用来打印异常信息
        }
        log.info("发送成功");
    }

}

