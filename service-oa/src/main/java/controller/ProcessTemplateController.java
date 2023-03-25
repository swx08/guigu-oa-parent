package controller;

import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.model.process.ProcessTemplate;
import com.guigu.service.ProcessTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:ProcessTemplateController
 * Package:com.guigu.controller
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/22 - 23:05
 * @Version:v1.0
 */
//接口测试路径http://localhost:8800/doc.html
@Api(value = "审批模板管理", tags = "审批模板管理")
@Slf4j
@RestController
@RequestMapping(value = "/admin/process/processTemplate")
public class ProcessTemplateController {
    @Autowired
    private ProcessTemplateService processTemplateService;

    //@PreAuthorize("hasAuthority('bnt.processTemplate.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result page(@PathVariable Long page,
                       @PathVariable Long limit) {
        log.info("获取审批模板管理分页列表请求！");
        Page<ProcessTemplate> pageParam = new Page<>(page, limit);
        //分页查询审批模板，但是要把审批类型对应的类型名查询出来（人事，出勤，财务）
        IPage<ProcessTemplate> pageModel = processTemplateService.selectPage(pageParam);
        return Result.ok(pageModel);
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.list')")
    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        log.info("获取单个审批模板管理请求！");
        ProcessTemplate processTemplate = processTemplateService.getById(id);
        return Result.ok(processTemplate);
    }
    //@PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody ProcessTemplate processTemplate) {
        log.info("新增审批模板管理请求！");
        processTemplateService.save(processTemplate);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody ProcessTemplate processTemplate) {
        log.info("修改审批模板管理请求！");
        processTemplateService.updateById(processTemplate);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('bnt.processTemplate.remove')")
    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        log.info("删除审批模板管理请求！");
        processTemplateService.removeById(id);
        return Result.ok();
    }

    /**
     * 上传至classes/process目录
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    @PreAuthorize("hasAuthority('bnt.processTemplate.templateSet')")
    @ApiOperation(value = "上传流程定义")
    @PostMapping("/uploadProcessDefinition")
    public Result uploadProcessDefinition(MultipartFile file) throws FileNotFoundException {
        String path = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
        log.info("path为:{}",path);

        String fileName = file.getOriginalFilename();
        // 上传目录
        File tempFile = new File(path + "/processes/");
        // 判断目录是否存着
        if (!tempFile.exists()) {
            tempFile.mkdirs();//创建目录
        }
        // 创建空文件用于写入文件
        File imageFile = new File(path + "/processes/" + fileName);
        // 保存文件流到本地
        try {
            file.transferTo(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("上传失败");
        }

        Map<String, Object> map = new HashMap<>();
        //根据上传地址后续部署流程定义，文件名称为流程定义的默认key
        map.put("processDefinitionPath", "processes/" + fileName);
        map.put("processDefinitionKey", fileName.substring(0, fileName.lastIndexOf(".")));
        return Result.ok(map);
    }

    /**
     * 流程部署定义
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('bnt.processTemplate.publish')")
    @ApiOperation(value = "发布")
    @GetMapping("/publish/{id}")
    public Result publish(@PathVariable Long id) {
        log.info("流程部署定义请求！");
        //发布后的模板状态为1
        processTemplateService.publish(id);
        return Result.ok();
    }
}
