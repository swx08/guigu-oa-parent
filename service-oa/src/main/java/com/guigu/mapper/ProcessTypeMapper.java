package com.guigu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.model.process.ProcessType;
import org.springframework.stereotype.Repository;

/**
 * ClassName:ProcessTypeMapper
 * Package:com.guigu.mapper
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/22 - 22:50
 * @Version:v1.0
 */

/**
 * 审批类型
 */
@Repository
public interface ProcessTypeMapper extends BaseMapper<ProcessType> {
}
