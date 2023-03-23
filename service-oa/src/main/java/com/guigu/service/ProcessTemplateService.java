package com.guigu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.model.process.ProcessTemplate;

/**
 * ClassName:ProcessTemplateService
 * Package:com.guigu.service
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/22 - 23:03
 * @Version:v1.0
 */

/**
 * 审批模板
 */
public interface ProcessTemplateService extends IService<ProcessTemplate> {
    /**
     * 获取审批模板
     * @param pageParam
     * @return
     */
    IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam);

    /**
     * 发布审批
     * @param id
     */
    void publish(Long id);
}
