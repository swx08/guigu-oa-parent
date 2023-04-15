package com.guigu.service.impl;

import com.guigu.GlobalExceptionHandler.GuiguException;
import com.guigu.config.securityconfig.custom.CustomUser;
import com.guigu.config.securityconfig.custom.UserDetailsService;
import com.guigu.model.system.SysUser;
import com.guigu.service.SysMenuService;
import com.guigu.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName:UserDetailsServiceImpl
 * Package:com.guigu.service.impl
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/12 - 21:49
 * @Version:v1.0
 */

/**
 * 根据用户名获取用户对象
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if(sysUser == null){
            throw new UsernameNotFoundException("用户名不存在！");
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("账号已停用");
        }
        //根据userId查询用户操作权限数据
        List<String> userPermsList = sysMenuService.findUserPermsListByUserId(sysUser.getId());
        //创建list集合，封装最终权限数据
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        //查询list集合遍历
        for (String perm : userPermsList) {
            authorityList.add(new SimpleGrantedAuthority(perm.trim()));
        }
        return new CustomUser(sysUser, authorityList);
    }
}
