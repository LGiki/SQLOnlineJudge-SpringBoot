package cn.edu.jmu.sqlonlinejudge.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.edu.jmu.sqlonlinejudge.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author sgh
 * @date 2019/6/20 18:35
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> findByUsername(@Param("username")String username);


}