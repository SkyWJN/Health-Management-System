package com.wujiajun.vo;

import lombok.Data;

/**
 * 微信小程序 加密数据
 * @author wujiajun
 * @date 2023/5/11/ 12:41
 */
@Data
public class Encrypted {
    /**
     * 加密数据
     */
    private String encryptedData;

    /**
     * 调用微信运动传递的矢量加密算法
     */
    private String iv;

    /**
     * 微信小程序登陆获取的会话key
     */
    private String sessionKey;

    /**
     * 微信小程序唯一标志
     */
    private String openid;

}
