package com.guigu.service.impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.mapper.ProcessTemplateMapper;
import com.guigu.model.process.ProcessTemplate;
import com.guigu.model.process.ProcessType;
import com.guigu.service.ProcessService;
import com.guigu.service.ProcessTemplateService;
import com.guigu.service.ProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:ProcessTemplateServiceImpl
 * Package:com.guigu.service.impl
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/22 - 23:03
 * @Version:v1.0
 */

/**
 * 审批模板
 */
@Service
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService {

    @Autowired
    private ProcessTemplateMapper processTemplateMapper;

    @Autowired
    private ProcessTypeService processTypeService;

    @Autowired
    private ProcessService processService;


    /**
     * 获取审批模板
     * @param pageParam
     * @return
     */
    @Override
    public IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam) {
        //1.调用mapper的方法实现分页查询
        LambdaQueryWrapper<ProcessTemplate> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ProcessTemplate::getId);
        Page<ProcessTemplate> processTemplatePage = processTemplateMapper.selectPage(pageParam, wrapper);

        //2.第一步分页查询返回分页数据，从分页数据说去列表list集合(返回的审批模板数据在page属性的Records中)
        List<ProcessTemplate> processTemplateList = processTemplatePage.getRecords();

        //3.遍历list集合,得到每个对象的审批类型id
        for (ProcessTemplate processTemplate : processTemplateList) {
            Long processTypeId = processTemplate.getProcessTypeId();
            LambdaQueryWrapper<ProcessType> processTypeWrapper = new LambdaQueryWrapper<>();
            processTypeWrapper.eq(ProcessType::getId,processTypeId);
            //4.根据审批类型id，查询获取对应类型名称
            ProcessType processType = processTypeService.getOne(processTypeWrapper);
            if(processType == null){
                continue;
            }
            //5.完成最终封装processTypeName
            processTemplate.setProcessTypeName(processType.getName());
        }
        return processTemplatePage;
    }

    /**
     * 发布审批
     * @param id
     */
    @Transactional
    @Override
    public void publish(Long id) {
        ProcessTemplate processTemplate = this.getById(id);
        //设置状态为1则表示发布，模板发布后不能进行修改或删除
        processTemplate.setStatus(1);
        processTemplateMapper.updateById(processTemplate);
        //TODO 部署流程定义，后续完善
//        if(!StringUtils.isEmpty(processTemplate.getProcessDefinitionPath())){
//            processService.deployByZip(processTemplate.getProcessDefinitionPath());
//        }
    }
}
