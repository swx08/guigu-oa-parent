package com.guigu.mapper;

/**
 * ClassName:MenuMapper
 * Package:com.guigu.mapper
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/24 - 8:58
 * @Version:v1.0
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guigu.model.wechat.Menu;
import org.springframework.stereotype.Repository;

/**
 * 微信菜单
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
}
