package cn.edu.jmu.sqlonlinejudge.mapper;

import cn.edu.jmu.sqlonlinejudge.model.User;import java.util.List;

/**
 * @author sgh
 * @date 2019/6/20 18:35
 */
public interface UserMapper {

    int insert(User record);

    int insertSelective(User record);

    /**
     * 通过id删除用户
     *
     * @param id 用户id
     * @return 删除个数
     */
    int deleteById(Integer id);

    /**
     * 通过id选择用户
     *
     * @param id 用户id
     * @return User 用户
     */
    User selectById(Integer id);

    /**
     * 更新用户
     *
     * @param record 用户信息
     * @return int 更新成功数量
     */
    int updateByIdSelective(User record);

    /**
     * 更新用户
     *
     * @param record 用户信息
     * @return int 更新成功数量
     */
    int updateById(User record);

    /**
     * 查询所有用户
     *
     * @return List<User> 用户列表
     */
    List<User> selectAll();
}