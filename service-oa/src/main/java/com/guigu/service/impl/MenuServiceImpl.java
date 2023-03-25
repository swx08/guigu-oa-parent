package com.guigu.service.impl;

/**
 * ClassName:MenuServiceImpl
 * Package:com.guigu.service.impl
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/24 - 9:01
 * @Version:v1.0
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.mapper.MenuMapper;
import com.guigu.model.wechat.Menu;
import com.guigu.service.MenuService;
import com.guigu.vo.wechat.MenuVo;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 微信菜单
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 微信获取全部菜单
     * @return
     */
    @Override
    public List<MenuVo> findMenuInfo() {
        //用于返回List<MenuVo>
        List<MenuVo> menuVoList = new ArrayList<>();

        //1.查询所有菜单List集合
        List<Menu> menuList = baseMapper.selectList(null);
        //2.查询所有一级菜单 parent_id = 0,返回一级菜单List集合
        List<Menu> FirstList = menuList.stream().filter(menu -> menu.getParentId().longValue() == 0)
                                                                .collect(Collectors.toList());
        //3.一级菜单list集合遍历，得到每个一级菜单
        for (Menu menu : FirstList) {
            //menu -> menuVo获取一级menuVo菜单
            MenuVo firstMenuVo = new MenuVo();
            BeanUtils.copyProperties(menu,firstMenuVo);
            //4.获取每个一级菜单里面所有二级菜单 id 和parent_id比较
            List<Menu> twoMenuList = menuList.stream().filter(item -> item.getParentId().longValue() == menu.getId())
                    .sorted(Comparator.comparing(Menu::getSort))
                    .collect(Collectors.toList());
            //5.把一级菜单里面所有二级菜单获取到，封装一级菜单children集合
            List<MenuVo> children = new ArrayList<>();
            for (Menu twoMenu : twoMenuList) {
                //twoMenu -> twoMenuVo获取二级twoMenuVo菜单
                MenuVo twoMenuVo = new MenuVo();
                BeanUtils.copyProperties(twoMenu,twoMenuVo);
                children.add(twoMenuVo);
            }
            firstMenuVo.setChildren(children);
            menuVoList.add(firstMenuVo);
        }
        return menuVoList;
    }

    /**
     * 微信推送菜单
     */
    @Override
    public void syncMenu() {
        List<MenuVo> menuVoList = this.findMenuInfo();
        //菜单
        JSONArray buttonList = new JSONArray();
        for(MenuVo oneMenuVo : menuVoList) {
            JSONObject one = new JSONObject();
            one.put("name", oneMenuVo.getName());
            if(CollectionUtils.isEmpty(oneMenuVo.getChildren())) {
                one.put("type", oneMenuVo.getType());
                one.put("url", "http://sixkey2.viphk.91tunnel.com/#"+oneMenuVo.getUrl());
            } else {
                JSONArray subButton = new JSONArray();
                for(MenuVo twoMenuVo : oneMenuVo.getChildren()) {
                    JSONObject view = new JSONObject();
                    view.put("type", twoMenuVo.getType());
                    if(twoMenuVo.getType().equals("view")) {
                        view.put("name", twoMenuVo.getName());
                        //H5页面地址
                        view.put("url", "http://sixkey2.viphk.91tunnel.com#"+twoMenuVo.getUrl());
                    } else {
                        view.put("name", twoMenuVo.getName());
                        view.put("key", twoMenuVo.getMeunKey());
                    }
                    subButton.add(view);
                }
                one.put("sub_button", subButton);
            }
            buttonList.add(one);
        }
        //菜单
        JSONObject button = new JSONObject();
        button.put("button", buttonList);
        try {
            wxMpService.getMenuService().menuCreate(button.toJSONString());
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除微信菜单
     */
    @SneakyThrows
    @Override
    public void removeMenu() {
        wxMpService.getMenuService().menuDelete();
    }
}
