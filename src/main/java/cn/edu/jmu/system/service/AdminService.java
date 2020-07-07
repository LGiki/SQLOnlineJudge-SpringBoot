package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.dto.AdminDto;
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
     * @param adminDto adminDto
     * @param page     page
     * @return IPage<AdminDto>
     */
    IPage<AdminDto> getAll(AdminDto adminDto, Page page);

    boolean delete(Long id);

    boolean insert(Admin admin);

    /**
     * 切换管理员状态：正常 <-> 正常
     *
     * @param id 管理员ID
     * @return boolean 操作是否执行成功
     */
    boolean toggleAdminStatus(Integer id);
}
