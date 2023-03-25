package controller.api;

import com.atguigu.common.result.Result;
import com.guigu.model.process.ProcessTemplate;
import com.guigu.model.process.ProcessType;
import com.guigu.service.ProcessService;
import com.guigu.service.ProcessTemplateService;
import com.guigu.service.ProcessTypeService;
import com.guigu.vo.process.ProcessFormVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName:ProcessApiController
 * Package:com.guigu.controller.api
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/23 - 14:40
 * @Version:v1.0
 */

/**
 * 员工端
 */
@Api(tags = "审批流管理")
@Slf4j
@RestController
@RequestMapping(value="/admin/process")
@CrossOrigin  //跨域
public class ProcessApiController {
    @Autowired
    private ProcessTypeService processTypeService;

    @Autowired
    private ProcessTemplateService processTemplateService;

    @Autowired
    private ProcessService processService;

    @ApiOperation(value = "获取全部审批分类及模板")
    @GetMapping("findProcessType")
    public Result findProcessType() {
        log.info("获取全部审批分类及模板请求！");
        List<ProcessType> processTypeList = processTypeService.findProcessType();
        return Result.ok(processTypeList);
    }

    @ApiOperation(value = "获取审批模板")
    @GetMapping("getProcessTemplate/{processTemplateId}")
    public Result get(@PathVariable Long processTemplateId) {
        log.info("获取审批模板请求！");
        ProcessTemplate processTemplate = processTemplateService.getById(processTemplateId);
        return Result.ok(processTemplate);
    }

    /**
     * 流程实例启动
     * @param processFormVo
     * @return
     */
    @ApiOperation(value = "启动流程")
    @PostMapping("/startUp")
    public Result start(@RequestBody ProcessFormVo processFormVo){
        log.info("启动流程请求！");
        processService.startUp(processFormVo);
        return Result.ok();
    }
}
