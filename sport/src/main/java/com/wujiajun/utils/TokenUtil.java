package com.wujiajun.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token工具类，用于生成token
 * 用户登陆拿到token然后访问我们的系统资源
 *
 * @author wujiajun
 * @date 2023/3/16/ 9:42
 */
@Component
public class TokenUtil {

    //秘钥
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration; //秒

    /**
     * 根据荷载信息生成token
     * @param map
     * @return
     */
    private String generateJwt(Map<String, Object> map) {
        return Jwts.builder()
                .setClaims(map)
                //秘钥,根据加密算法和自定义的秘钥，生成
                .signWith(SignatureAlgorithm.HS512, secret)
                //token过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 传入用户信息，生成token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("username", userDetails.getUsername());
        map.put("created", new Date());

        return this.generateJwt(map);
    }

    /**
     * 根据token获取荷载信息
     * @param token
     * @return
     */
    public Claims getTokenBody(String token){
        //token过期可能，捕获异常
        try{
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 根据token的到用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token){
        return (String) this.getTokenBody(token).get("username");
    }

    /**
     * 根据token判断当前时间内，该token是否过期
     * @param token
     * @return
     */
    public boolean isExpiration(String token){
        return this.getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 刷新token令牌
     * @param token
     * @return
     */
    public String refreshToken(String token){
        Claims claims = this.getTokenBody(token);
        claims.setExpiration(new Date());
        return this.generateJwt(claims);
    }




}
