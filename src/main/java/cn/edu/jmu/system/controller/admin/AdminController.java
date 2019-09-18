package cn.edu.jmu.system.controller.admin;


import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.EncryptUtil;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.dto.AdminDto;
import cn.edu.jmu.system.service.AdminService;
import cn.edu.jmu.system.service.mapper.AdminMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        AdminDto adminDto = AdminMapper.toDto(admin);
        return ResponseUtil.buildResponse("查询成功", adminDto);
    }

    /**
     * 通过用户ID删除管理员
     *
     * @param id 用户ID
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable("id") Integer id) {
        // 删除用户
        boolean success = adminService.removeById(id);
        return ResponseUtil.buildResponse(success, "删除成功", "删除失败");
    }

    /**
     * 更新管理员信息
     *
     * @param adminDto 新的管理员信息
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> update(@RequestBody @Validated AdminDto adminDto, @PathVariable(value = "id") Integer id) {
        if (adminDto.getId() != null && adminDto.getId().equals(id)) {
            // 更新用户信息
            Admin admin = AdminMapper.toEntity(adminDto);
            boolean success = adminService.saveOrUpdate(admin);
            return ResponseUtil.buildResponse(success, "更新管理员信息成功", "更新管理员信息失败");
        } else {
            return ResponseUtil.fail("id不一致");
        }
    }

    /**
     * 添加管理员
     *
     * @param adminDto 新的管理员
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insert(@RequestBody @Validated AdminDto adminDto) {
        Admin admin = AdminMapper.toEntity(adminDto);
        String salt = EncryptUtil.generatorSalt();
        admin.setSalt(salt);
//            admin.setPassword(EncryptUtil.encryption(admin.getUsername(), admin.getPassword(), salt));
        boolean success = adminService.insert(admin);
        return ResponseUtil.buildResponse(success, "新增管理员成功", "新增管理员失败");
    }
}
