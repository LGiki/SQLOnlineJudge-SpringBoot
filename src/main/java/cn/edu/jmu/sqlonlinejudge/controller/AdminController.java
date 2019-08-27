package cn.edu.jmu.sqlonlinejudge.controller;


import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.entity.Admin;
import cn.edu.jmu.sqlonlinejudge.entity.dto.AdminDto;
import cn.edu.jmu.sqlonlinejudge.service.AdminService;
import cn.edu.jmu.sqlonlinejudge.service.mapper.AdminMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/admins")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 查询所有管理员
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getAll(AdminDto adminDto,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        Page<Admin> page = new Page<>(pageNum, pageSize);
        IPage<AdminDto> iPage = adminService.getAll(adminDto, page);
        basicResponse.wrapper(AbstractResponseCode.OK, "查询成功", iPage);
        return ResponseEntity.ok().body(basicResponse);
    }

    /**
     * 通过ID查询管理员详情
     *
     * @param id 用户ID
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> selectAdminById(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        Admin admin = adminService.getById(id);
        AdminDto adminDto = AdminMapper.toDto(admin);
        response.wrapper(AbstractResponseCode.OK, "查询成功", adminDto);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 通过用户ID删除管理员
     *
     * @param id 用户ID
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        // 删除用户
        if (adminService.removeById(id)) {
            response.wrapper(AbstractResponseCode.OK, "删除成功");
        } else {
            response.wrapper(AbstractResponseCode.FAIL, "删除失败");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 更新管理员信息
     *
     * @param adminDto 新的管理员信息
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> update(@RequestBody AdminDto adminDto, @PathVariable(value = "id") Integer id) {
        BasicResponse response = new BasicResponse();
        if (adminDto != null && adminDto.getId() != null && adminDto.getId().equals(id)) {
            // 更新用户信息
            Admin admin = AdminMapper.toEntity(adminDto);
            if (adminService.saveOrUpdate(admin)) {
                response.wrapper(AbstractResponseCode.OK, "更新管理员信息成功", admin);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "更新管理员信息失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 添加管理员
     *
     * @param adminDto 新的管理员
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insert(@RequestBody @Validated AdminDto adminDto) {
        BasicResponse response = new BasicResponse();
        if (adminDto != null && adminDto.getId() == null) {
            Admin admin = AdminMapper.toEntity(adminDto);
//            String salt = EncryptUtil.generatorSalt();
//            admin.setSalt(salt);
//            admin.setPassword(EncryptUtil.encryption(admin.getUsername(), admin.getPassword(), salt));
            if (adminService.saveOrUpdate(admin)) {
                response.wrapper(AbstractResponseCode.OK, "新增管理员成功", admin);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "新增管理员失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
