package cn.edu.jmu.sqlonlinejudge.serviceimpl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import cn.edu.jmu.sqlonlinejudge.model.User;
import cn.edu.jmu.sqlonlinejudge.mapper.UserMapper;
import cn.edu.jmu.sqlonlinejudge.service.UserService;

/**
 * @author sgh
 * @date 2019/6/18 18:55
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Boolean deleteById(Integer id) {
        return userMapper.deleteById(id) == 1;
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
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public Boolean updateByIdSelective(User record) {
        return userMapper.updateByIdSelective(record) == 1;
    }

    @Override
    public Boolean updateById(User record) {
        return userMapper.updateById(record) == 1;
    }

}
