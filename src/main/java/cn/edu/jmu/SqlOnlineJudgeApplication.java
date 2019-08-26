package cn.edu.jmu;

import cn.edu.jmu.security.config.ShiroConfig;
import cn.edu.jmu.security.filter.CrossDomainFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author lgiki
 */
// @ServletComponentScan("cn.edu.jmu.security.filter")
@SpringBootApplication
@MapperScan("cn.edu.jmu.sqlonlinejudge.mapper")
@Import({ShiroConfig.class, CrossDomainFilter.class})
public class SqlOnlineJudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlOnlineJudgeApplication.class, args);
    }

}
