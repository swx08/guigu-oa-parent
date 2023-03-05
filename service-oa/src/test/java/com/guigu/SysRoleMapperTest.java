package com.guigu;

import com.guigu.mapper.SysRoleMapper;
import com.guigu.model.system.SysRole;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
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
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void mapperTest(){
        List<SysRole> list = sysRoleMapper.selectList(null);
        System.out.println(list);
    }
}
