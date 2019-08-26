package cn.edu.jmu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lgiki
 */
// @ServletComponentScan("cn.edu.jmu.security.filter")
// @Import({ShiroConfig.class, CrossDomainFilter.class})
@SpringBootApplication
@MapperScan("cn.edu.jmu.sqlonlinejudge.mapper")
public class SqlOnlineJudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlOnlineJudgeApplication.class, args);
    }
}
