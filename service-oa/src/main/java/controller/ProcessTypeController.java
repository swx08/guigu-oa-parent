package controller;

import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.model.process.ProcessType;
import com.guigu.service.ProcessTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:ProcessTypeController
 * Package:com.guigu.controller
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/22 - 22:57
 * @Version:v1.0
 */
//接口测试路径http://localhost:8800/doc.html
@Api(value = "审批类型", tags = "审批类型")
@Slf4j
@RestController
@RequestMapping(value = "/admin/process/processType")
public class ProcessTypeController {
    @Autowired
    private ProcessTypeService processTypeService;

    @PreAuthorize("hasAuthority('bnt.processType.list')")
    @ApiOperation(value = "获取审批类型分页列表")
    @GetMapping("{page}/{limit}")
    public Result page(@PathVariable Long page,@PathVariable Long limit){
        log.info("获取审批类型分页列表请求！");
        Page<ProcessType> pageInfo = new Page<>(page, limit);
        Page<ProcessType> typePage = processTypeService.page(pageInfo);
        return Result.ok(typePage);
    }

    @PreAuthorize("hasAuthority('bnt.processType.list')")
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        log.info("获取单个审批类型请求！");
        ProcessType processType = processTypeService.getById(id);
        return Result.ok(processType);
    }

    @PreAuthorize("hasAuthority('bnt.processType.add')")
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody ProcessType processType){
        log.info("新增审批类型请求！");
        processTypeService.save(processType);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.processType.update')")
    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody ProcessType processType){
        log.info("修改审批类型请求！");
        processTypeService.updateById(processType);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.processType.remove')")
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        log.info("删除审批类型请求！");
        processTypeService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "获取全部审批分类")
    @GetMapping("findAll")
    public Result findAll(){
        log.info("获取全部审批分类请求！");
        List<ProcessType> processTypeList = processTypeService.list();
        return Result.ok(processTypeList);
    }
}
