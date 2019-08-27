package cn.edu.jmu.sqlonlinejudge.controller;

import cn.edu.jmu.common.response.AbstractResponseCode;
import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.sqlonlinejudge.entity.Database;
import cn.edu.jmu.sqlonlinejudge.entity.dto.DatabaseDto;
import cn.edu.jmu.sqlonlinejudge.service.DatabaseService;
import cn.edu.jmu.sqlonlinejudge.service.mapper.DatabaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author LGiki
 * @date 2019/06/23 09:27
 */
@RestController
@RequiresRoles(value = {"admin"})
@RequestMapping("/api/databases")
public class DatabaseController {

    @Resource
    private DatabaseService databaseService;

    /**
     * 查询所有数据库
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getAll(DatabaseDto databaseDto,
                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        BasicResponse basicResponse = new BasicResponse();
        Page<Database> page = new Page<>(pageNum, pageSize);
        IPage<DatabaseDto> iPage = databaseService.getAll(databaseDto, page);
        basicResponse.wrapper(AbstractResponseCode.OK, "查询成功", iPage);
        return ResponseEntity.ok().body(basicResponse);
    }


    /**
     * 通过ID查询数据库详情
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getDatabaseById(@PathVariable("id") Integer id) {
        BasicResponse basicResponse = new BasicResponse();
        Database database = databaseService.getById(id);
        DatabaseDto databaseDto = DatabaseMapper.toDto(database);
        basicResponse.wrapper(AbstractResponseCode.OK, "查询成功", databaseDto);
        return ResponseEntity.ok().body(basicResponse);
    }

    /**
     * 通过ID删除数据库
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        BasicResponse response = new BasicResponse();
        // 删除数据库
        if (databaseService.removeById(id)) {
            response.wrapper(AbstractResponseCode.OK, "删除成功");
        } else {
            response.wrapper(AbstractResponseCode.FAIL, "删除失败");
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 添加数据库
     */
    @PostMapping(value = "/")
    public ResponseEntity<BasicResponse> insertDatabase(@RequestBody @Validated DatabaseDto databaseDto) {
        BasicResponse response = new BasicResponse();
        if (databaseDto != null && databaseDto.getId() == null) {
            Database database = DatabaseMapper.toEntity(databaseDto);
            //新数据库要将是否已生成字段设置为 false
            database.setIsCreated(false);
            if (databaseService.saveOrUpdate(database)) {
                response.wrapper(AbstractResponseCode.OK, "新增数据库成功", database);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "新增数据库失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 通过ID更新数据库
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> updateDatabaseById(@PathVariable("id") Integer id,
                                                            @RequestBody DatabaseDto databaseDto) {
        BasicResponse response = new BasicResponse();
        if (databaseDto != null && databaseDto.getId() != null && databaseDto.getId().equals(id)) {
            // 更新数据库信息
            Database database = DatabaseMapper.toEntity(databaseDto);
            if (databaseService.saveOrUpdate(database)) {
                response.wrapper(AbstractResponseCode.OK, "更新数据库信息成功", database);
            } else {
                response.wrapper(AbstractResponseCode.FAIL, "更新数据库信息失败");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * 获取数据库数量
     */
    @GetMapping(value = "/count")
    public ResponseEntity<BasicResponse> count() {
        BasicResponse response = new BasicResponse();
        response.wrapper(AbstractResponseCode.OK, "获取数量成功", databaseService.count());
        return ResponseEntity.ok().body(response);
    }
}
