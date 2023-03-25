package com.guigu.service;

/**
 * ClassName:MenuService
 * Package:com.guigu.service
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/24 - 9:00
 * @Version:v1.0
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.model.wechat.Menu;
import com.guigu.vo.wechat.MenuVo;

import java.util.List;

/**
 * 微信菜单
 */
public interface MenuService extends IService<Menu> {
    /**
     * 微信获取全部菜单
     * @return
     */
    List<MenuVo> findMenuInfo();

    /**
     * 微信推送菜单
     */
    void syncMenu();

    /**
     * 删除微信菜单
     */
    void removeMenu();
}
