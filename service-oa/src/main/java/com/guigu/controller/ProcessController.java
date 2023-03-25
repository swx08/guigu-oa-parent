package com.guigu.controller;

import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.model.process.Process;
import com.guigu.service.ProcessService;
import com.guigu.service.SysUserService;
import com.guigu.vo.process.ApprovalVo;
import com.guigu.vo.process.ProcessQueryVo;
import com.guigu.vo.process.ProcessVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:ProcessController
 * Package:com.guigu.controller
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/23 - 13:04
 * @Version:v1.0
 */
//接口测试路径http://localhost:8800/doc.html
@Api(tags = "审批流管理")
@Slf4j
@RestController
@RequestMapping(value = "/admin/process")
public class ProcessController {
    @Autowired
    private ProcessService processService;

    @Autowired
    private SysUserService sysUserService;

    @PreAuthorize("hasAuthority('bnt.process.list')")
    @ApiOperation(value = "获取审批流分页列表")
    @GetMapping("{page}/{limit}")
    public Result page(@PathVariable Long page,
                       @PathVariable Long limit,
                       ProcessQueryVo processQueryVo){
        log.info("获取审批流分页列表请求！");
        Page<ProcessVo> pageInfo = new Page<>(page, limit);
        IPage<ProcessVo> pageModel = processService.selectPage(pageInfo,processQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "待处理")
    @GetMapping("/findPending/{page}/{limit}")
    public Result findPending(@PathVariable Long page,
                              @PathVariable Long limit) {
        log.info("待处理请求!");
        Page<Process> pageParam = new Page<>(page, limit);
        IPage<ProcessVo> pageModel = processService.findPending(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取审批详情")
    @GetMapping("show/{id}")
    public Result show(@PathVariable Long id){
        log.info("获取审批详情请求！");
        Map<String,Object> map = processService.show(id);
        return Result.ok(map);
    }

    @ApiOperation(value = "审批")
    @PostMapping("approve")
    public Result approve(@RequestBody ApprovalVo approvalVo){
        log.info("审批请求！");
        processService.approve(approvalVo);
        return Result.ok();
    }

    @ApiOperation(value = "已处理")
    @GetMapping("/findProcessed/{page}/{limit}")
    public Result findProcessed(@PathVariable Long page, @PathVariable Long limit) {
        log.info("审批已处理请求！");
        Page<Process> pageParam = new Page<>(page, limit);
        IPage<ProcessVo> pageModel = processService.findProcessed(pageParam);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "已发起")
    @GetMapping("/findStarted/{page}/{limit}")
    public Result findStarted(@PathVariable Long page,
                              @PathVariable Long limit) {
        log.info("审批已发起请求！");
        Page<ProcessVo> pageParam = new Page<>(page, limit);
        IPage<ProcessVo> pageModel = processService.findStarted(pageParam);
        return Result.ok(pageModel);
    }

    /**
     * 基本信息
     * @return
     */
    @ApiOperation(value = "获取当前用户基本信息")
    @GetMapping("getCurrentUser")
    public Result getCurrentUser() {
        log.info("获取当前用户基本信息请求！");
        Map<String,Object> map = sysUserService.getCurrentUser();
        return Result.ok(map);
    }
}
