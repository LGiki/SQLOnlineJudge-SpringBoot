package cn.edu.jmu.sqlonlinejudge.service;

import cn.edu.jmu.sqlonlinejudge.entity.Admin;
import cn.edu.jmu.sqlonlinejudge.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sgh
 * @since 2019-08-19
 */
public interface AdminService extends IService<Admin> {

    /**
     * 得到所有管理员
     *
     * @param admin admin
     * @param page  page
     * @return IPage<User>
     */
    IPage<Admin> get(Admin admin, Page page);

}
