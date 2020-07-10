package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.dto.AdminDto;
import cn.edu.jmu.system.service.AdminService;
import cn.edu.jmu.system.service.converter.AdminConverter;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 管理员控制器
 *
 * @author sgh
 * @since 2019-08-19
 */
@RestController
@RequiresRoles(value = {"admin"})
@RequestMapping("/api/admin/admins")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 查询所有管理员
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getAll(AdminDto adminDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Admin> page = new Page<>(pageNum, pageSize);
        IPage<AdminDto> iPage = adminService.getAll(adminDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 通过ID查询管理员详情
     *
     * @param id 用户ID
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> selectAdminById(@PathVariable("id") Integer id) {
        Admin admin = adminService.getById(id);
        AdminDto adminDto = AdminConverter.toDto(admin);
        return ResponseUtil.buildResponse("查询成功", adminDto);
    }

    /**
     * 更新管理员状态
     */
    @PutMapping(value = "/status/{id}")
    public ResponseEntity<BasicResponse> status(@PathVariable("id") Integer id) {
        boolean success = adminService.toggleAdminStatus(id);
        return ResponseUtil.buildResponse(success, "更改成功", "更改失败");
    }

    /**
     * 更新管理员密码
     *
     * @param password 新密码
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> password(@RequestParam(name = "password") String password, @PathVariable(value = "id") Integer id) {
        Admin admin = adminService.getById(id);
        if (admin == null) {
            return ResponseUtil.fail("该管理员ID不存在");
        }
        admin.setPassword(EncryptUtil.encryption(admin.getUsername(), password, admin.getSalt()));
        boolean success = adminService.saveOrUpdate(admin);
        return ResponseUtil.buildResponse(success, "更新管理员信息成功", "更新管理员信息失败");
    }

    /**
     * 添加管理员
     *
     * @param adminDto 新的管理员
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insert(@RequestBody @Validated AdminDto adminDto) {
        Admin byId = adminService.getOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getUsername, adminDto.getUsername()));
        if (ObjectUtil.isNotNull(byId)) {
            return ResponseUtil.fail("该管理员已存在");
        }
        if (ObjectUtils.isEmpty(adminDto.getPassword())) {
            return ResponseUtil.fail("密码不能为空");
        }
        Admin admin = AdminConverter.toEntity(adminDto);
        String salt = EncryptUtil.generatorSalt();
        admin.setSalt(salt);
        admin.setPassword(EncryptUtil.encryption(admin.getUsername(), admin.getPassword(), salt));
        boolean success = adminService.insert(admin);
        return ResponseUtil.buildResponse(success, "新增管理员成功", "新增管理员失败");
    }
}
