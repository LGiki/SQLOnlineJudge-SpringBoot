package cn.edu.jmu.sqlonlinejudge.mapper;

import cn.edu.jmu.sqlonlinejudge.model.Database;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;

public class DatabaseMapperTest {
    private static DatabaseMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(DatabaseMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/DatabaseMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(DatabaseMapper.class, builder.openSession(true));
    }

    @Test
    public void testUpdateByIdSelective() throws FileNotFoundException {
        Database database = new Database();
        database.setId(4);
        database.setCreateTable("4");
        database.setName("4");
        database.setTestData("4");
        database.setIsCreated(false);
        mapper.updateByIdSelective(database);
    }
}
