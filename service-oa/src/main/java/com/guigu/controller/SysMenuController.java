package com.guigu.controller;

import com.atguigu.common.result.Result;
import com.guigu.model.system.SysMenu;
import com.guigu.service.SysMenuService;
import com.guigu.vo.system.AssginMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
//接口测试路径http://localhost:8800/doc.html
@Api("菜单管理接口")
@Slf4j
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {
        log.info("根据角色获取菜单请求！");
        List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assignMenuVo) {
        log.info("给角色分配权限请求！");
        sysMenuService.doAssign(assignMenuVo);
        return Result.ok();
    }

    @ApiOperation(value = "获取菜单列表接口")
    @GetMapping("findNodes")
    public Result findNodes() {
        log.info("获取菜单列表接口请求！");
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu) {
        log.info("新增菜单请求！");
        sysMenuService.save(sysMenu);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        log.info("修改菜单请求！");
        sysMenuService.updateById(sysMenu);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        log.info("删除菜单请求！");
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }

}
