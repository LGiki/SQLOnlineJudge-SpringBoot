package cn.edu.jmu.sqlonlinejudge.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.edu.jmu.sqlonlinejudge.model.User;
import cn.edu.jmu.sqlonlinejudge.mapper.UserMapper;
import cn.edu.jmu.sqlonlinejudge.serviceimpl.UserService;
/**
 *
 *
 * @author sgh
 * @date 2019/6/20 18:35
 */
@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByIdSelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateById(record);
    }

}
