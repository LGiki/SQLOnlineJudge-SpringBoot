package cn.edu.jmu.sqlonlinejudge.mapper;

import cn.edu.jmu.sqlonlinejudge.model.User;

/**
 * 用户映射器
 *
 * @author sgh
 * @date 2019/6/18 18:55
 */
public interface UserMapper {
    /**
     * 通过id删除用户
     *
     * @param id 用户id
     * @return 删除个数
    */
    int deleteById(Integer id);

    /**
     * 插入用户
     *
     * @param record 用户信息
     * @return int 插入成功数量
    */
    int insert(User record);

    /**
     * 插入用户
     *
     * @param record 用户信息
     * @return int 插入成功数量
    */
    int insertSelective(User record);

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

}