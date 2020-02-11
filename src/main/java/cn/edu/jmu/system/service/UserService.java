package cn.edu.jmu.system.service;

import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.dto.UserDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
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
     * @param userDto userDto
     * @param page    page
     * @return IPage<User>
     */
    IPage<UserDto> getAll(UserDto userDto, Page page, SFunction<User, ?> column, Boolean orderByDesc);

    /**
     * 更新用户
     *
     * @param userDto userDto
     * @return boolean
     */
    boolean update(UserDto userDto);

    /**
     * 更改用户状态
     *
     * @param id id
     * @return boolean
     */
    boolean changeUserStatus(Integer id);
}
