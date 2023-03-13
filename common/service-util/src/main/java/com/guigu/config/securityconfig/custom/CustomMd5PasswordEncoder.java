package com.guigu.config.securityconfig.custom;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

/**
 * ClassName:CustomMd5PasswordEncoder
 * Package:com.guigu.config.securityconfig.custom
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/12 - 21:27
 * @Version:v1.0
 */

/**
 * 自定义加密处理组件
 */
@Component
public class CustomMd5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
    }
}
