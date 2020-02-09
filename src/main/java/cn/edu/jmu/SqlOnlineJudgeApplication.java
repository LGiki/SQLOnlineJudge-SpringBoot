package cn.edu.jmu;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lgiki
 */

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("cn.edu.jmu.system.mapper")
public class SqlOnlineJudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlOnlineJudgeApplication.class, args);
    }
}

