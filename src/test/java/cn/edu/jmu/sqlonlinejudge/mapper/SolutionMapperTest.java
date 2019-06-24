package cn.edu.jmu.sqlonlinejudge.mapper;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;


/**
 * @author sgh
 * @date 2019/6/24 18:50
 */
public class SolutionMapperTest {

    private static SolutionMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(SolutionMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/SolutionMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(SolutionMapper.class, builder.openSession(true));
    }

    @Test
    public void testSelectAll() throws FileNotFoundException {
        System.out.println(mapper.selectAll());
    }
}