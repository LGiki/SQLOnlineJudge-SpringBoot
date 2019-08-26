package cn.edu.jmu.sqlonlinejudge;

import cn.edu.jmu.sqlonlinejudge.entity.Database;
import cn.edu.jmu.sqlonlinejudge.mapper.DatabaseMapper;
import cn.edu.jmu.sqlonlinejudge.service.AdminService;
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

    @Test
    @Transactional
    public void contextLoads() {

        Page<Database> databasePage = new Page<>(1, 10);
        databasePage.setOptimizeCountSql(false);
        IPage<Database> iPage = databaseMapper.selectPage(databasePage, null);

        iPage.getRecords().forEach(System.out::println);
    }

}
