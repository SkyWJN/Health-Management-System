package com.wujiajun.controller;

import com.wujiajun.service.SysUserService;
import com.wujiajun.utils.*;
import com.wujiajun.vo.MailVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

/**
 * @author wujiajun
 * @date 2023/4/26/ 16:14
 */
@RestController
@RequestMapping("/tool")
public class ToolController {

    @Autowired
    private QiniuUtils qiniuUtils;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private SysUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "七牛云文件上传")
    @PostMapping("/upload")
    public Result uplaod(@RequestBody MultipartFile file) throws IOException {
        String url = qiniuUtils.upload(file.getBytes(), file.getOriginalFilename());
//        String url = qiniuUtils.upload(file.getInputStream(), file.getOriginalFilename());
        return Result.success("文件上传成功！", url);

    }

    @ApiOperation(value = "忘记密码？邮件找回")
    @PostMapping("/forget/password")
    public Result forget(@RequestBody MailVo mail) {
        mail.setSubject("个人运动管理平台");
        Random random = new Random();
        int password = 100000 + random.nextInt(1000000);
        userService.updatePwdByMail(mail.getReceivers()[0], passwordEncoder.encode(MD5Utils.md5(String.valueOf(password))));
        mail.setContent("您的新密码：" + password + " ，请妥善保管！");
        String result = mailUtils.sendMail(mail);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!result = " + result);
        return Result.success(result);
    }

}
