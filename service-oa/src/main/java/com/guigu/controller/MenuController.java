package com.guigu.controller;

/**
 * ClassName:MenuController
 * Package:com.guigu.controller
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/24 - 8:57
 * @Version:v1.0
 */

import com.atguigu.common.result.Result;
import com.guigu.model.wechat.Menu;
import com.guigu.service.MenuService;
import com.guigu.vo.wechat.MenuVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信菜单
 */
@Slf4j
@RestController
@RequestMapping("/admin/wechat/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "获取全部菜单")
    @GetMapping("findMenuInfo")
    public Result findMenuInfo(){
        log.info("微信获取全部菜单请求！");
        List<MenuVo> menuVoList = menuService.findMenuInfo();
        return Result.ok(menuVoList);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return Result.ok(menu);
    }

    //@PreAuthorize("hasAuthority('bnt.menu.add')")
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Menu menu) {
        menuService.save(menu);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('bnt.menu.update')")
    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return Result.ok();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        menuService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "微信推送菜单")
    @GetMapping("syncMenu")
    public Result createMenu() {
        log.info("微信推送菜单请求!");
        menuService.syncMenu();
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.menu.removeMenu')")
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("removeMenu")
    public Result removeMenu() {
        menuService.removeMenu();
        return Result.ok();
    }
}
