package controller;

import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.model.system.SysRole;
import com.guigu.service.SysRoleService;
import com.guigu.vo.system.AssginRoleVo;
import com.guigu.vo.system.SysRoleQueryVo;
import com.sun.org.apache.bcel.internal.generic.DREM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
@Slf4j
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public Result findAll(){
        log.info("查询所有角色请求！");
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    @ApiOperation("分页查询角色")
    //page为当前页码，limit为当前页的展示数量
    @GetMapping("{page}/{limit}")
    //@EnableGlobalMethodSecurity注解，来判断用户对某个控制层的方法是否具有访问权限
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    public Result page(@PathVariable Long page,
                       @PathVariable Long limit,
                       SysRoleQueryVo sysRoleQueryVo){
        log.info("分页查询角色请求！");
        //分页构造器
        Page<SysRole> pageInfo = new Page<>(page,limit);
        //查询构造器
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        //根据角色名进行模糊查询
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)){
            wrapper.like(SysRole::getRoleName,roleName);
        }
        //根据创建时间降序查询
        wrapper.orderByDesc(SysRole::getCreateTime);
        //执行分页查询
        Page<SysRole> sysRolePage = sysRoleService.page(pageInfo, wrapper);
        return Result.ok(sysRolePage);
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("get/{id}")
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    public Result getById(@PathVariable Long id){
        log.info("获取单个角色信息请求！");
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }

    @ApiOperation("新增角色")
    @PostMapping("save")
    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    //spring-boot中可以用@validated来校验数据，如果数据异常则会统一抛出异常，方便异常中心统一处理。
    public Result save(@RequestBody @Validated SysRole sysRole){
        log.info("新增角色请求！");
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
    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    public Result updateById(@RequestBody SysRole sysRole){
        log.info("修改角色请求！");
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
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @CacheEvict(value = "sysRole",allEntries = true)
    public Result remove(@PathVariable Long id) {
        log.info("删除角色请求！");
        sysRoleService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    @Transactional
    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @CacheEvict(value = "sysRole",allEntries = true)
    public Result batchRemove(@RequestBody List<Long> idList) {
        log.info("批量删除角色请求！");
        sysRoleService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId) {
        log.info("根据用户获取角色数据请求！");
        Map<String, Object> roleMap = sysRoleService.findRoleByAdminId(userId);
        return Result.ok(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        log.info("根据用户分配角色请求！");
        sysRoleService.doAssign(assginRoleVo);
        return Result.ok();
    }
}
