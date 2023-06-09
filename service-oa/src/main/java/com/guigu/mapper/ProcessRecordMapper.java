package com.guigu.mapper;

/**
 * ClassName:ProcessRecordMapper
 * Package:com.guigu.mapper
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/23 - 20:10
 * @Version:v1.0
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.model.process.ProcessRecord;
import org.springframework.stereotype.Repository;

/**
 * 流程历史记录
 */
@Repository
public interface ProcessRecordMapper extends BaseMapper<ProcessRecord> {
}
