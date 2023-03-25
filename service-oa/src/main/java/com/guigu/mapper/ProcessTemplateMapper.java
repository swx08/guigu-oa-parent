package com.guigu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.model.process.ProcessTemplate;
import org.springframework.stereotype.Repository;

/**
 * ClassName:ProcessTemplateMapper
 * Package:com.guigu.mapper
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/22 - 23:01
 * @Version:v1.0
 */

/**
 * 审批模板
 */
@Repository
public interface ProcessTemplateMapper extends BaseMapper<ProcessTemplate> {
}
