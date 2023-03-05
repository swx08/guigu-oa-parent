package com.guigu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.mapper.SysUserMapper;
import com.guigu.model.system.SysUser;
import com.guigu.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * ClassName:SysUserServiceImpl
 * Package:com.guigu.service.impl
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 13:32
 * @Version:v1.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
