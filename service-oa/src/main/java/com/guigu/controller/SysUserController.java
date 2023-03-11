package com.guigu.controller;

import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.model.system.SysUser;
import com.guigu.service.SysUserService;
import com.guigu.vo.system.SysUserQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName:SysUserController
 * Package:com.guigu.controller
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 13:33
 * @Version:v1.0
 */
//接口测试路径http://localhost:8800/doc.html
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation("分页查询用户")
    @GetMapping("{page}/{limit}")
    //在方法执行前spring先查看缓存中是否有数据，如果有数据，则直接返回缓存数据；
    @Cacheable(value = "sysUser",key = "#sysUserQueryVo.keyword + '_' + #sysUserQueryVo.roleId")
    public Result page(@PathVariable Long page,
                       @PathVariable Long limit,
                       SysUserQueryVo sysUserQueryVo){
        //构造分页构造器
        Page<SysUser> pageInfo = new Page<>(page,limit);
        //封装条件，判断条件值不为空
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        //获取条件值
        String username = sysUserQueryVo.getKeyword();
        String createTimeBegin = sysUserQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysUserQueryVo.getCreateTimeEnd();
        //判断条件值不为空
        //like 模糊查询
        if(!StringUtils.isEmpty(username)) {
            wrapper.like(SysUser::getUsername,username);
        }
        //ge 大于等于
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge(SysUser::getCreateTime,createTimeBegin);
        }
        //le 小于等于
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le(SysUser::getCreateTime,createTimeEnd);
        }

        //调用mp的方法实现条件分页查询
        IPage<SysUser> pageModel = sysUserService.page(pageInfo, wrapper);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("save")
    //将一条或多条数据从缓存中删除,新增或删除或修改时，需要将套餐下的所有缓存数据删除
    @CacheEvict(value = "sysUser",allEntries = true)
    public Result save(@RequestBody SysUser sysUser){
        //将密码进行md5加密处理
        String password = sysUser.getPassword();
        String md5PassWord = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(md5PassWord);
        boolean save = sysUserService.save(sysUser);
        if(save){
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("update")
    //将一条或多条数据从缓存中删除,新增或删除或修改时，需要将套餐下的所有缓存数据删除
    @CacheEvict(value = "sysUser",allEntries = true)
    public Result update(@RequestBody SysUser sysUser){
        boolean update = sysUserService.updateById(sysUser);
        if(update){
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("remove/{id}")
    //将一条或多条数据从缓存中删除,新增或删除或修改时，需要将套餐下的所有缓存数据删除
    @CacheEvict(value = "sysUser",allEntries = true)
    public Result delete(@PathVariable Long id){
        boolean remove = sysUserService.removeById(id);
        if(remove){
            return Result.ok();
        }
        return Result.fail();
    }
}
