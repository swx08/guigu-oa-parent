package com.guigu.controller;

import com.atguigu.common.jwt.JwtHelper;
import com.atguigu.common.result.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guigu.GlobalExceptionHandler.GuiguException;
import com.guigu.model.system.SysUser;
import com.guigu.service.SysMenuService;
import com.guigu.service.SysUserService;
import com.guigu.vo.system.LoginVo;
import com.guigu.vo.system.RouterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台登录登出
 * </p>
 */
//接口测试路径http://localhost:8800/doc.html
@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class LoginController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登录生成token,并返回token
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        //1.获取用户名
        String username = loginVo.getUsername();

        //2.查询数据库
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);

        //2.1获取唯一一个值
        SysUser sysUser = sysUserService.getOne(wrapper);

        //3.用户信息是否存在
        if(sysUser == null){
            throw new GuiguException(210,"用户不存在!");
        }

        //4.判断密码
        //将获取的密码进行md5加密处理
        String password = loginVo.getPassword();
        String md5PassWord = DigestUtils.md5DigestAsHex(password.getBytes());

        //数据库中的密码是经过md5加密处理的
        if(!md5PassWord.equals(sysUser.getPassword())){
            throw new GuiguException(201,"密码错误!");
        }

        //5.判断用户是否被禁用
        if(sysUser.getStatus() == 0 ){
            throw new GuiguException(201,"用户已被禁用!");
        }
        //6.使用jwt根据用户id和用户名生成token字符串
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        //7.返回token字符串
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }
    /**
     * 登录的同时获取用户可以操作的菜单列表和操作按钮
     * @return
     */
    @ApiOperation("登录获取信息")
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        //1.从请求头里获取用户信息(获取请求头token字符串)
        String token = request.getHeader("token");

        //2.从token字符串中解密获取用户id 或者 用户名
        Long userId = JwtHelper.getUserId(token);

        //3.根据用户id查询数据库,把用户信息获取出来
        SysUser sysUser = sysUserService.getById(userId);

        //4.根据用户id获取用户可以操作的菜单列表
        //4.1查询数据库，动态构建路由结构进行展示
        List<RouterVo> routerVoList = sysMenuService.findUserMenuListByUserId(userId);

        //5.根据用户id获取用户可以操作的操作按钮
        //5.1获取操作按钮的权限标识
        List<String> permsList = sysMenuService.findUserPermsListByUserId(userId);

        //6.返回相应的数据
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[swx]");
        map.put("name",sysUser.getName());
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");

        //返回用户可以操作菜单列表
        map.put("routers",routerVoList);
        map.put("buttons",permsList);
        return Result.ok(map);
    }
    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }

}