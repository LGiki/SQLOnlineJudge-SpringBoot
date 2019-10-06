package cn.edu.jmu.system;

import cn.edu.jmu.judge.executor.thread.JudgeCallable;
import cn.edu.jmu.judge.service.impl.JudgeServiceImpl;
import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.mapper.DatabaseMapper;
import cn.edu.jmu.system.service.AdminService;
import cn.edu.jmu.system.service.impl.UserProblemServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    private AdminService adminService;

    @Resource
    private DatabaseMapper databaseMapper;


    @Resource
    private JudgeServiceImpl judgeService;

    @Resource
    private UserProblemServiceImpl userProblemService;

    @Test
    @Transactional
    public void contextLoads() {

        Page<Database> databasePage = new Page<>(1, 10);
        databasePage.setOptimizeCountSql(false);
        IPage<Database> iPage = databaseMapper.selectPage(databasePage, null);

        iPage.getRecords().forEach(System.out::println);
    }



    @Test
    @Transactional
    public void test() {
//        boolean b = userProblemService.isExist(7, 1);
//        System.out.println(b ? "存在" : "不存在");
        JudgeCallable thread = new JudgeCallable(4);

    }

}
