package com.guigu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.model.process.ProcessType;

import java.util.List;

/**
 * ClassName:ProcessTypeService
 * Package:com.guigu.service
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/22 - 22:51
 * @Version:v1.0
 */

/**
 * 审批类型
 */
public interface ProcessTypeService extends IService<ProcessType> {
    /**
     * 获取全部审批分类及模板
     * @return
     */
    List<ProcessType> findProcessType();
}
