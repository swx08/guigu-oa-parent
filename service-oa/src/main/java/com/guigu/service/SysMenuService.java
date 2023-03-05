package com.guigu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.model.system.SysMenu;
import com.guigu.vo.system.AssginMenuVo;

import java.util.List;

/**
 * ClassName:SysMenu
 * Package:com.guigu.service
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 14:24
 * @Version:v1.0
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 获取菜单列表接口
     * @return
     */
    List<SysMenu> findNodes();

    /**
     * 如果有子菜单则不能删除
     * @param id
     */
    void removeMenuById(Long id);

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 给角色分配权限
     * @param assignMenuVo
     */
    void doAssign(AssginMenuVo assignMenuVo);
}