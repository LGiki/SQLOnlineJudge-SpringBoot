package cn.edu.jmu.sqlonlinejudge;

import cn.edu.jmu.sqlonlinejudge.service.PermissionService;
import cn.edu.jmu.sqlonlinejudge.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SqlOnlineJudgeApplicationTests {
    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Test
    @Transactional
    public void contextLoads() {
        System.out.println(roleService.findAllRoleByAdminId(1));
        System.out.println(permissionService.findAllPermissionByRoleId(1));
    }

}
