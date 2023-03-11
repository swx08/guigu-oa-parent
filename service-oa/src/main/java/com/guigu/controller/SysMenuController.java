package com.guigu.controller;

import com.atguigu.common.result.Result;
import com.guigu.model.system.SysMenu;
import com.guigu.service.SysMenuService;
import com.guigu.vo.system.AssginMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:SysMenuController
 * Package:com.guigu.controller
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 14:32
 * @Version:v1.0
 */
@Api("菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {
        List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assignMenuVo) {
        sysMenuService.doAssign(assignMenuVo);
        return Result.ok();
    }

    @ApiOperation(value = "获取菜单列表接口")
    @GetMapping("findNodes")
    @Cacheable(value = "sysMenu",key = "'sysMenu_1'")
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    @CacheEvict(value = "sysMenu",allEntries = true)
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    @CacheEvict(value = "sysMenu",allEntries = true)
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    @CacheEvict(value = "sysMenu",allEntries = true)
    public Result remove(@PathVariable Long id) {
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }

}
