package com.guigu.mapper;

/**
 * ClassName:ProcessMapper
 * Package:com.guigu.mapper
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/23 - 12:56
 * @Version:v1.0
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guigu.model.process.Process;
import com.guigu.vo.process.ProcessQueryVo;
import com.guigu.vo.process.ProcessVo;
import org.apache.ibatis.annotations.Param;

/**
 * 审批流管理
 */
public interface ProcessMapper extends BaseMapper<Process> {
    /**
     * 获取审批流分页列表
     * @param pageInfo
     * @param processQueryVo
     * @return
     */
    public IPage<ProcessVo> selectPage(Page<ProcessVo> pageInfo,@Param("vo") ProcessQueryVo processQueryVo);
}
