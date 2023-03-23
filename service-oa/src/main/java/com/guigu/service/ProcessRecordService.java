package com.guigu.service;

/**
 * ClassName:ProcessRecordService
 * Package:com.guigu.service
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/23 - 20:11
 * @Version:v1.0
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.model.process.ProcessRecord;

/**
 * 流程历史记录
 */
public interface ProcessRecordService extends IService<ProcessRecord> {
    /**
     * 保存当前流程记录
     * @param processId
     * @param status
     * @param description
     */
    void record(Long processId,Integer status,String description);
}
