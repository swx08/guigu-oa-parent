package com.guigu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.model.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * ClassName:SysRoleMapper
 * Package:com.guigu.mapper
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/3 - 22:41
 * @Version:v1.0
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
}
