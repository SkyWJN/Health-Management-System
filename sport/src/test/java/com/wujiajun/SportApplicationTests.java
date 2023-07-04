package com.wujiajun;

import com.wujiajun.utils.MD5Utils;
import com.wujiajun.utils.MailUtils;
import com.wujiajun.vo.MailVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SportApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailUtils mailUtils;

    @Test
    void contextLoads() {
        String encode = passwordEncoder.encode(MD5Utils.md5("123456"));
        System.out.println("md5:" + MD5Utils.md5("123456"));
        System.out.println("encode = " + encode);
        boolean matches = passwordEncoder.matches(MD5Utils.md5("123456"), encode);
        System.out.println("matches = " + matches);

    }

    @Test
    void testMail(){
        MailVo mailVo = new MailVo();
        mailVo.setReceivers(new String[]{"qq1744881633@163.com"});
        mailVo.setHtml(true);
        mailVo.setSubject("个人运动管理平台");
        mailVo.setContent("<a href='https://www.baidu.com/' style='color: red'>邮件发送测试</a>");
        System.out.println("mailUtils.sendMail(mailVo) = " + mailUtils.sendMail(mailVo));

    }

}
