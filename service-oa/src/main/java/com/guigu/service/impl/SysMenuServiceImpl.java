package com.guigu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.GlobalExceptionHandler.GuiguException;
import com.guigu.mapper.SysMenuMapper;
import com.guigu.model.system.SysMenu;
import com.guigu.model.system.SysRoleMenu;
import com.guigu.service.SysMenuService;
import com.guigu.service.SysRoleMenuService;
import com.guigu.utils.MenuHelper;
import com.guigu.vo.system.AssginMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:SysMenuImpl
 * Package:com.guigu.service.impl
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 14:25
 * @Version:v1.0
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 获取菜单列表接口
     * @return
     */
    @Override
    public List<SysMenu> findNodes() {
        //1 查询所有菜单数据
        List<SysMenu> sysMenuList = baseMapper.selectList(null);

        //2 构建树形结构
//        {
//            第一层
//            children:[
//            {
//                第二层
//                        ....
//            }
//            ]
//        }
        List<SysMenu> resultList = MenuHelper.buildTree(sysMenuList);
        return resultList;
    }

    /**
     * 如果有子菜单则不能删除
     * @param id
     */
    @Override
    public void removeMenuById(Long id) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId,id);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GuiguException(201,"此菜单已绑定，不能删除");
        }
        baseMapper.deleteById(id);
    }

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //1.查询所有菜单，添加添加status = 1
        LambdaQueryWrapper<SysMenu> sysMenuWrapper = new LambdaQueryWrapper<>();
        sysMenuWrapper.eq(SysMenu::getStatus,1);
        List<SysMenu> AllSysMenuList = baseMapper.selectList(sysMenuWrapper);

        //2.根据角色id roleId查询，角色菜单关系表里面 角色id对应所有的菜单id
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuWrapper.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(sysRoleMenuWrapper);
        //3.根据获取菜单id，获取对应菜单对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(item -> item.getMenuId()).collect(Collectors.toList());
        //3.1 拿着菜单id 和所有菜单集合里面id进行比较，如果相同封装
        AllSysMenuList.stream().forEach(item ->{
            if(menuIdList.contains(item.getId())){
                item.setSelect(true);
            }else {
                item.setSelect(false);
            }
        });
        //4.返回规定树形显示格式菜单列表
        List<SysMenu> sysMenuList = MenuHelper.buildTree(AllSysMenuList);
        return sysMenuList;
    }

    /**
     * 给角色分配权限
     * @param assignMenuVo
     */
    @Override
    public void doAssign(AssginMenuVo assignMenuVo) {
        //1.根据角色id 删除菜单角色表 分配数据
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuLambdaQueryWrapper.eq(SysRoleMenu::getRoleId,assignMenuVo.getRoleId());
        sysRoleMenuService.remove(sysRoleMenuLambdaQueryWrapper);
        //2.从参数assginMenuVo里面获取角色新分配id列表
        //  进行遍历，把每个id数据添加菜单角色表
        List<Long> menuIdList = assignMenuVo.getMenuIdList();
        for (Long menuId : menuIdList) {
            if(StringUtils.isEmpty(menuId)){
                continue;
            }
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenuService.save(sysRoleMenu);
        }
    }
}
