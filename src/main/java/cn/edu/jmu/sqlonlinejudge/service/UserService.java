package cn.edu.jmu.sqlonlinejudge.service;

import cn.edu.jmu.sqlonlinejudge.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author sgh
 * @date 2019/6/18 18:55
 */
public interface UserService extends IService<User> {

    /**
     * 得到所有用户
     *
     * @param user user
     * @param page page
     * @return IPage<User>
     */
    IPage<User> get(User user, Page page);

    /**
     * 更新用户
     *
     * @param user user
     * @return boolean
     */
    boolean update(User user);
}
