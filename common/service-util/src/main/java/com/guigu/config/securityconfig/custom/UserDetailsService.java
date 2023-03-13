package com.guigu.config.securityconfig.custom;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * ClassName:UserDetailsService
 * Package:com.guigu.config.securityconfig.custom
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/12 - 21:46
 * @Version:v1.0
 */
public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService{
    /**
     * 根据用户名获取用户对象（获取不到直接抛异常）
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
