package com.guigu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.model.system.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName:SysMenu
 * Package:com.guigu.mapper
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 14:23
 * @Version:v1.0
 */
@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 通过userId查询菜单列表
     * @param userId
     * @return
     */
    List<SysMenu> findUserMenuListByUserId(Long userId);
}
