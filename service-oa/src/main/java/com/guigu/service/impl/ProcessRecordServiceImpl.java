package com.guigu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.ThreadLocal.LoginUserInfoHelper;
import com.guigu.mapper.ProcessRecordMapper;
import com.guigu.model.process.ProcessRecord;
import com.guigu.model.system.SysUser;
import com.guigu.service.ProcessRecordService;
import com.guigu.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:ProcessRecordServiceImpl
 * Package:com.guigu.service.impl
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/23 - 20:11
 * @Version:v1.0
 */

/**
 * 流程历史记录
 */
@Service
public class ProcessRecordServiceImpl extends ServiceImpl<ProcessRecordMapper, ProcessRecord> implements ProcessRecordService {

    @Autowired
    private ProcessRecordMapper processRecordMapper;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 保存当前流程记录
     * @param processId
     * @param status
     * @param description
     */
    @Override
    public void record(Long processId, Integer status, String description) {
        SysUser sysUser = sysUserService.getById(LoginUserInfoHelper.getUserId());
        ProcessRecord processRecord = new ProcessRecord();
        processRecord.setProcessId(processId);
        processRecord.setStatus(status);
        processRecord.setDescription(description);
        processRecord.setOperateUserId(sysUser.getId());
        processRecord.setOperateUser(sysUser.getName());
        processRecordMapper.insert(processRecord);
    }
}
