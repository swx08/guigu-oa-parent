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
import com.guigu.vo.system.RouterVo;
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
        //1.查询所有菜单，获取状态status = 1的菜单
        LambdaQueryWrapper<SysMenu> sysMenuWrapper = new LambdaQueryWrapper<>();
        sysMenuWrapper.eq(SysMenu::getStatus,1);
        List<SysMenu> AllSysMenuList = baseMapper.selectList(sysMenuWrapper);

        //2.根据角色roleId查询，角色菜单关系表里面 角色roleId对应所有的菜单id
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuWrapper.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(sysRoleMenuWrapper);
        //3.根据获取菜单id，获取对应菜单对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(
                item -> item.getMenuId()).collect(Collectors.toList());
        //3.1 拿着菜单id 和所有菜单集合里面id进行比较，如果相同封装
        AllSysMenuList.stream().forEach(item ->{
            if(menuIdList.contains(item.getId())){
                item.setSelect(true);
            }else {
                item.setSelect(false);
            }
        });
        //4.返回规定树形格式显示菜单列表
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
        //  进行遍历，把每个menuId和RoleId数据添加到角色菜单表
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

    /**
     * 根据用户id获取用户可以操作的菜单列表
     * @param userId
     * @return
     */
    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userId) {
        //菜单列表
        List<SysMenu> sysMenuList = null;
        //1.判断当前用户是否是管理员  userId=1是管理员
        if(userId.longValue() == 1){
            //1.1 如果是管理员，则查询所有菜单列表
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            //status为1就是可以运作的菜单
            wrapper.eq(SysMenu::getStatus,1);
            //升序排序
            wrapper.orderByAsc(SysMenu::getSortValue);
            sysMenuList = baseMapper.selectList(wrapper);
        }else {
            //1.2 如果不是管理员，根据userId查询可以操作的菜单列表
            //多表联查：用户角色关系表、角色菜单关系表、菜单表(需要自己写sql，不再借助mybatis-plus)
            sysMenuList = baseMapper.findUserMenuListByUserId(userId);
        }
        //3. 把查询出来的数据列表构建成框架要求的路由结构
        //3.1 使用菜单操作工具类构建树形结构
        List<SysMenu> buildTree = MenuHelper.buildTree(sysMenuList);
        //3.2 构建成框架要求的路由结构
        List<RouterVo> buildRouterList = this.buildRouter(buildTree);
        return null;
    }

    /**
     * 构建成框架要求的路由结构
     * @param buildTree
     * @return
     */
    private List<RouterVo> buildRouter(List<SysMenu> buildTree) {
        return null;
    }

    /**
     * 根据用户id获取用户可以操作的操作按钮
     * 获取操作按钮的权限标识
     * @param userId
     * @return
     */
    @Override
    public List<String> findUserPermsListByUserId(Long userId) {
        //1.判断当前用户是否是管理员  userId=1是管理员
        //1.1 如果是管理员，则查询所有按钮列表

        //1.2 如果不是管理员，根据userId查询可以操作的按钮列表

        //2. 多表联查：用户角色关系表、角色菜单关系表、菜单表(需要自己写sql，不再借助mybatis-plus)

        //3. 从查询出来的数据里面，获取可以操作的按钮值的list集合，进行返回
        return null;
    }
}
