package com.guigu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.model.system.SysRole;
import com.guigu.vo.system.AssginRoleVo;

import java.util.Map;

/**
 * ClassName:SysRoleService
 * Package:com.guigu.service
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/4 - 0:13
 * @Version:v1.0
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据用户获取角色数据
     * @param userId
     * @return
     */
    Map<String,Object> findRoleByAdminId(Long userId);

    /**
     * 分配角色
     * @param assginRoleVo
     */
    void doAssign(AssginRoleVo assginRoleVo);
}
