package cn.edu.jmu.sqlonlinejudge.serviceimpl;

import cn.edu.jmu.sqlonlinejudge.model.User;
    /**
 *
 *
 * @author sgh
 * @date 2019/6/20 18:35
 */
public interface UserService{


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
