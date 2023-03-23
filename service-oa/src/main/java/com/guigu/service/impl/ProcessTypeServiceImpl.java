package com.guigu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.mapper.ProcessTypeMapper;
import com.guigu.model.process.ProcessTemplate;
import com.guigu.model.process.ProcessType;
import com.guigu.service.ProcessTemplateService;
import com.guigu.service.ProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:ProcessTypeServiceImpl
 * Package:com.guigu.service.impl
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/22 - 22:55
 * @Version:v1.0
 */

/**
 * 审批类型
 */
@Service
public class ProcessTypeServiceImpl extends ServiceImpl<ProcessTypeMapper, ProcessType> implements ProcessTypeService {

    @Autowired
    private ProcessTemplateService processTemplateService;

    /**
     * 获取全部审批分类及模板
     * @return
     */
    @Override
    public List<ProcessType> findProcessType() {
        //1.查询所有审批分类，返回List集合
        List<ProcessType> processTypeList = baseMapper.selectList(null);
        //2.遍历返回所有审批分类List集合
        for (ProcessType processType : processTypeList) {
            //3.得到每个审批分类，根据审批分类id查询对应审批模板
            Long typeId = processType.getId();
            LambdaQueryWrapper<ProcessTemplate> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProcessTemplate::getProcessTypeId,typeId);
            //4.根据审批分类id查询对应审批模板数据(list)封装到每个审批分类对象里面
            List<ProcessTemplate> processTemplateList = processTemplateService.list(wrapper);
            processType.setProcessTemplateList(processTemplateList);
        }
        return processTypeList;
    }
}
