package cn.edu.jmu.sqlonlinejudge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.edu.jmu.sqlonlinejudge.mapper")
@SpringBootApplication
public class SqlOnlineJudgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlOnlineJudgeApplication.class, args);
    }

}
