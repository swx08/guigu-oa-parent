package com.guigu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.model.system.SysUser;

import java.util.Map;

/**
 * ClassName:SysUserService
 * Package:com.guigu.service
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 13:32
 * @Version:v1.0
 */

/**
 * 用户
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 更新用户状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Integer status);

    /**
     * 根据用户名获取用户对象
     * @param username
     * @return
     */
    SysUser getByUsername(String username);

    /**
     * 基本信息
     * @return
     */
    Map<String, Object> getCurrentUser();

}
