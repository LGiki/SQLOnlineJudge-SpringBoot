package cn.edu.jmu.sqlonlinejudge.service;

import java.util.List;

import cn.edu.jmu.sqlonlinejudge.model.User;

/**
 * @author sgh
 * @date 2019/6/18 18:55
 */
public interface UserService {

    /**
     * 通过id查找
     *
     * @param id 用户id
     * @return 用户
     */
    User selectById(Integer id);

    /**
     * 通过id删除用户
     *
     * @param id 用户id
     * @return 删除个数
     */
    Boolean deleteById(Integer id);

    /**
     * 插入用户
     *
     * @param record 用户信息
     * @return int 插入成功数量
     */
    Boolean insert(User record);

    /**
     * 更新用户
     *
     * @param record 用户信息
     * @return int 更新成功数量
     */
    Boolean updateById(User record);

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
     * @param password 密码
     * @return User 用户
     */
    User verify(String username, String password);


    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return User 查找到的用户对象
     */
    User findByUsername(String username);
}
