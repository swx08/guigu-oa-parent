package com.guigu;

import com.guigu.mapper.SysRoleMapper;
import com.guigu.model.system.SysRole;
import com.guigu.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * ClassName:SysRoleMapperTest
 * Package:com.guigu
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/3 - 22:43
 * @Version:v1.0
 */
@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    private SysRoleService service;

    @Test
    public void serviceTest(){
        List<SysRole> list = service.list();
        System.out.println(list);
    }
}
