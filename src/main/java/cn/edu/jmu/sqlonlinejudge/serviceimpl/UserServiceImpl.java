package cn.edu.jmu.sqlonlinejudge.serviceimpl;

import cn.edu.jmu.sqlonlinejudge.mapper.UserMapper;
import cn.edu.jmu.sqlonlinejudge.model.User;
import cn.edu.jmu.sqlonlinejudge.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sgh
 * @date 2019/6/18 18:55
 */
@Service
public class UserServiceImpl implements UserService,  UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Override
    public Boolean deleteById(Integer id) {
        return userMapper.deleteById(id) == 1;
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public Boolean insert(User record) {
        return userMapper.insert(record) == 1;
    }

    @Override
    public Boolean updateById(User record) {
        return userMapper.updateById(record) == 1;
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User verify(String username, String password) {
        return userMapper.verify(username, password);
    }

    @Override
    public List<User> selectBySelective(User record) {
        return userMapper.selectBySelective(record);
    }

	@Override
	public User findByUsername(String username){
		 return userMapper.findByUsername(username);
	}

	@Override
	public List<User> selectAllOrderBySolvedDesc(){
		 return userMapper.selectAllOrderBySolvedDesc();
	}

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(s);
        if(user == null) {
            return new User();
        }
        return user;
    }

	@Override
	public List<User> selectAllByKeyword(String keyword){
		 return userMapper.selectAllByKeyword(keyword);
	}

	@Override
	public Integer countAll(){
		 return userMapper.countAll();
	}

}
