package com.guigu.controller;

import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.model.system.SysRole;
import com.guigu.service.SysRoleService;
import com.guigu.vo.system.SysRoleQueryVo;
import com.sun.org.apache.bcel.internal.generic.DREM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:SysRoleController
 * Package:com.guigu.controller
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/4 - 0:21
 * @Version:v1.0
 */
//接口测试路径http://localhost:8800/doc.html
@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public Result findAll(){
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    @ApiOperation("分页查询角色")
    //page为当前页码，limit为当前页的展示数量
    @GetMapping("{page}/{limit}")
    //在方法执行前spring先查看缓存中是否有数据，如果有数据，则直接返回缓存数据；
    @Cacheable(value = "sysRole",key = "#sysRoleQueryVo.roleName + '_1' ")
    public Result page(@PathVariable Long page,
                       @PathVariable Long limit,
                       SysRoleQueryVo sysRoleQueryVo){
        //分页构造器
        Page<SysRole> pageInfo = new Page<>(page,limit);
        //查询构造器
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        //根据角色名进行模糊查询
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)){
            wrapper.like(SysRole::getRoleName,roleName);
        }
        //执行分页查询
        Page<SysRole> sysRolePage = sysRoleService.page(pageInfo, wrapper);
        return Result.ok(sysRolePage);
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    @ApiOperation("新增角色")
    @PostMapping("save")
    //将一条或多条数据从缓存中删除,新增或删除或修改时，需要将套餐下的所有缓存数据删除
    @CacheEvict(value = "sysRole",allEntries = true)
    //spring-boot中可以用@validated来校验数据，如果数据异常则会统一抛出异常，方便异常中心统一处理。
    public Result save(@RequestBody @Validated SysRole sysRole){
        if (sysRole != null){
            boolean save = sysRoleService.save(sysRole);
            if(save){
                return Result.ok();
            }
        }
        return Result.fail();
    }

    @ApiOperation("修改角色")
    @PutMapping("update")
    //将一条或多条数据从缓存中删除,新增或删除或修改时，需要将套餐下的所有缓存数据删除
    @CacheEvict(value = "sysRole",allEntries = true)
    public Result updateById(@RequestBody SysRole sysRole){
        if(sysRole != null){
            boolean update = sysRoleService.updateById(sysRole);
            if(update){
                return Result.ok();
            }
        }
        return Result.fail();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    @Transactional
    //将一条或多条数据从缓存中删除,新增或删除或修改时，需要将套餐下的所有缓存数据删除
    @CacheEvict(value = "sysRole",allEntries = true)
    public Result remove(@PathVariable Long id) {
        sysRoleService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    @Transactional
    //将一条或多条数据从缓存中删除,新增或删除或修改时，需要将套餐下的所有缓存数据删除
    @CacheEvict(value = "sysRole",allEntries = true)
    public Result batchRemove(@RequestBody List<Long> idList) {
        sysRoleService.removeByIds(idList);
        return Result.ok();
    }
}
