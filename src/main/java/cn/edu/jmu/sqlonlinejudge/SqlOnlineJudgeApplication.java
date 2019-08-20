package cn.edu.jmu.sqlonlinejudge;

import cn.edu.jmu.sqlonlinejudge.util.JwtTokenUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan("cn.edu.jmu.sqlonlinejudge.filter")
@SpringBootApplication
@EnableConfigurationProperties({JwtTokenUtil.class})
public class SqlOnlineJudgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlOnlineJudgeApplication.class, args);
    }

}
