package com.atguigu.common.jwt;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * ClassName:JwtHelper
 * Package:com.atguigu.common.jwt
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/11 - 23:03
 * @Version:v1.0
 */

/**
 * jwt工具类
 */
public class JwtHelper {
    //token有效时间
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    //token签名加密的密钥
    private static String tokenSignKey = "123456";

    /**
     * 生成token
     * @param userId
     * @param username
     * @return
     */
    public static String createToken(Long userId, String username) {
        String token = Jwts.builder()
                //分类
                .setSubject("AUTH-USER")
                //设置token有效时长
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                //设置主体部分(有效载荷)
                .claim("userId", userId)
                .claim("username", username)
                //签名部分
                //对密钥进行加密处理
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                //对生成的token字符串进行压缩
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    /**
     * 解析请求头里的token字符串的信息获取用户userId
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Integer userId = (Integer) claims.get("userId");
            return userId.longValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解析请求头里的token字符串的信息获取用户名userName
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) return "";

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
