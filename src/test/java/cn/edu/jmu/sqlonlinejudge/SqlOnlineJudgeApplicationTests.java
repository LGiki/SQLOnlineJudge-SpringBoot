package cn.edu.jmu.sqlonlinejudge;

import cn.edu.jmu.sqlonlinejudge.mapper.AdminMapper;
import cn.edu.jmu.sqlonlinejudge.mapper.UserMapper;
import cn.edu.jmu.sqlonlinejudge.model.User;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlOnlineJudgeApplicationTests {
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Test
    @Transactional
    public void contextLoads() {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, "1"));
        System.out.println(user);
        User one = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, "1"));
        System.out.println(one);
    }

}
