package cn.edu.jmu.sqlonlinejudge.mapper;

import org.apache.ibatis.annotations.Param;

import cn.edu.jmu.sqlonlinejudge.model.User;

import java.util.List;

/**
 * @author sgh
 * @date 2019/6/20 18:35
 */
public interface UserMapper {

    /**
     * 插入用户信息
     *
     * @param record 用户信息
     * @return int 插入个数
     */
    int insert(User record);

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
    int updateById(User record);

    /**
     * 查询所有用户
     *
     * @return List<User> 用户列表
     */
    List<User> selectAll();

    /**
     * 有选择的查询用户
     *
     * @param record 用户信息
     * @return List<User> 用户列表
     */
    List<User> selectBySelective(User record);

    /**
     * 验证用户
     *
     * @param username 用户名
     * @param password 用户密码
     * @return User 用户
     */
    User verify(@Param("username") String username, @Param("password") String password);


    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return User 查找到的用户对象
     */
    User findByUsername(@Param("username") String username);

    /**
     * 查询所有用户并按通过数降序排序
     *
     * @return List<User> 用户列表
     */
    List<User> selectAllOrderBySolvedDesc();

    /**
     * 模糊查询用户
     *
     * @param keyword 关键字
     * @return List<User> 用户列表
     */
    List<User> selectAllByKeyword(@Param("keyword") String keyword);


    /**
     * 查询用户数量
     *
     * @return Integer 用户数量
     */
    Integer countAll();


}