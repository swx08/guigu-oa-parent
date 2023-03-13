package com.guigu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.model.system.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ClassName:SysUserMapper
 * Package:com.guigu.mapper
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 13:31
 * @Version:v1.0
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
}
