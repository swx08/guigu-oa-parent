package com.guigu.service;

/**
 * ClassName:ProcessService
 * Package:com.guigu.service
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/23 - 12:58
 * @Version:v1.0
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.vo.process.ApprovalVo;
import com.guigu.vo.process.ProcessFormVo;
import com.guigu.vo.process.ProcessQueryVo;
import com.guigu.model.process.Process;
import com.guigu.vo.process.ProcessVo;

import java.util.Map;

/**
 * 审批流管理
 */
public interface ProcessService extends IService<Process> {
    /**
     * 获取审批流分页列表
     * @param pageInfo
     * @param processQueryVo
     * @return
     */
    IPage<ProcessVo> selectPage(Page<ProcessVo> pageInfo, ProcessQueryVo processQueryVo);

    /**
     * 部署流程定义
     * @param deployPath
     */
    void deployByZip(String deployPath);

    /**
     * 流程实例启动
     * @param processFormVo
     */
    void startUp(ProcessFormVo processFormVo);

    /**
     * 查询待处理列表
     * @param pageParam
     * @return
     */
    IPage<ProcessVo> findPending(Page<Process> pageParam);

    /**
     * 获取审批详情
     * @param id
     * @return
     */
    Map<String, Object> show(Long id);

    /**
     * 审批
     * @param approvalVo
     */
    void approve(ApprovalVo approvalVo);

    /**
     * 审批已处理
     * @param pageParam
     * @return
     */
    IPage<ProcessVo> findProcessed(Page<Process> pageParam);

    /**
     * 审批已发起
     * @param pageParam
     * @return
     */
    IPage<ProcessVo> findStarted(Page<ProcessVo> pageParam);
}
