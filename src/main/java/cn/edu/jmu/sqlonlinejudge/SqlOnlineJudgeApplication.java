package cn.edu.jmu.sqlonlinejudge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("cn.edu.jmu.sqlonlinejudge.mapper")
@ServletComponentScan("cn.edu.jmu.sqlonlinejudge.filter")
@SpringBootApplication
public class SqlOnlineJudgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlOnlineJudgeApplication.class, args);
    }

}
