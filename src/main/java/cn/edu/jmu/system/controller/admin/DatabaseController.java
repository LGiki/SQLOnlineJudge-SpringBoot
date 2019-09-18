package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.entity.dto.DatabaseDto;
import cn.edu.jmu.system.service.DatabaseService;
import cn.edu.jmu.system.service.mapper.DatabaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */
@RestController
@RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
@RequestMapping("/api/admin/databases")
public class DatabaseController {

    @Resource
    private DatabaseService databaseService;

    /**
     * 查询所有数据库
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getAll(DatabaseDto databaseDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Database> page = new Page<>(pageNum, pageSize);
        IPage<DatabaseDto> iPage = databaseService.getAll(databaseDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 通过ID查询数据库详情
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getDatabaseById(@PathVariable("id") Integer id) {
        Database database = databaseService.getById(id);
        DatabaseDto databaseDto = DatabaseMapper.toDto(database);
        return ResponseUtil.buildResponse("查询成功", databaseDto);
    }

    /**
     * 通过ID删除数据库
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable("id") Integer id) {
        // 删除数据库
        boolean success = databaseService.removeById(id);
        return ResponseUtil.buildResponse(success, "删除成功", "删除失败");
    }

    /**
     * 添加数据库
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insertDatabase(@RequestBody @Validated DatabaseDto databaseDto) {
        Database database = DatabaseMapper.toEntity(databaseDto);
        boolean success = databaseService.saveOrUpdate(database);
        return ResponseUtil.buildResponse(success, "新增数据库成功", "新增数据库失败");
    }

    /**
     * 通过ID更新数据库
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> updateDatabaseById(@PathVariable("id") Integer id, @RequestBody @Validated DatabaseDto databaseDto) {
        if (databaseDto.getId() != null && databaseDto.getId().equals(id)) {
            // 更新数据库信息
            Database database = DatabaseMapper.toEntity(databaseDto);
            boolean success = databaseService.add(databaseDto) && databaseService.saveOrUpdate(database);
            return ResponseUtil.buildResponse(success, "更新数据库成功", "更新数据库信息失败");
        } else {
            return ResponseUtil.fail("id不一致");
        }
    }

    /**
     * 获取数据库数量
     */
    @GetMapping(value = "/count")
    public ResponseEntity<BasicResponse> count() {
        int count = databaseService.count();
        return ResponseUtil.buildResponse(count);
    }
}
