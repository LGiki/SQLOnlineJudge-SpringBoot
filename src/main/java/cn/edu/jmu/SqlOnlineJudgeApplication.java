package cn.edu.jmu;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lgiki
 */

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("cn.edu.jmu.sqlonlinejudge.mapper")
public class SqlOnlineJudgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlOnlineJudgeApplication.class, args);
    }

    public static void test(String[] args) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.execfile("");
        PyFunction func = interpreter.get("test2", PyFunction.class);
        PyObject pyobj = func.__call__();
        System.out.println("answer = " + pyobj.toString());
    }
}

