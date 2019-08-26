package cn.edu.jmu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lgiki
 */
// @ServletComponentScan("cn.edu.jmu.security.filter")
@SpringBootApplication
@MapperScan("cn.edu.jmu.sqlonlinejudge.mapper")
//@Import({ShiroConfig.class, CrossDomainFilter.class})
public class SqlOnlineJudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlOnlineJudgeApplication.class, args);
    }
}
