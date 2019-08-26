package cn.edu.jmu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lgiki
 */

@SpringBootApplication
@MapperScan("cn.edu.jmu.sqlonlinejudge.mapper")
public class SqlOnlineJudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlOnlineJudgeApplication.class, args);
    }
}
