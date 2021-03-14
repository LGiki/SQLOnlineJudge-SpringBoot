package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.system.api.database.DatabaseListResponse;
import cn.edu.jmu.system.entity.Database;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.dto.DatabaseDto;
import cn.edu.jmu.system.service.DatabaseService;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.converter.DatabaseConverter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private ProblemService problemService;

    /**
     * 查询所有数据库
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getDatabaseList(DatabaseDto databaseDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Database> page = new Page<>(pageNum, pageSize);
        IPage<DatabaseListResponse> iPage = databaseService.getDatabaseList(databaseDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<BasicResponse> getAllDatabaseList() {
        return ResponseUtil.buildResponse("查询成功", databaseService.getAll());
    }

    /**
     * 通过ID查询数据库详情
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getDatabaseById(@PathVariable("id") Integer id) {
        Database database = databaseService.getById(id);
        DatabaseDto databaseDto = DatabaseConverter.toDto(database);
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
        Database database = DatabaseConverter.toEntity(databaseDto);
        if (databaseService.saveOrUpdate(database)) {
            databaseDto.setId(database.getId());
            JudgeResultJson judgeResultJson = databaseService.add(databaseDto);
            if ("0".equals(judgeResultJson.getCode())) {
                return ResponseUtil.ok("新增数据库成功");
            } else {
                databaseService.removeById(database.getId());
                return ResponseUtil.fail("建表失败," + judgeResultJson.getMessage());
            }
        } else {
            databaseService.removeById(database.getId());
            return ResponseUtil.fail("新增数据库失败");
        }
    }

    /**
     * 通过ID更新数据库
     */
    @PutMapping(value = "/{databaseId}")
    public ResponseEntity<BasicResponse> updateDatabaseById(@PathVariable("databaseId") Integer databaseId, @RequestBody @Validated DatabaseDto databaseDto) {
        if (!databaseService.existById(databaseId)) {
            return ResponseUtil.fail("数据库ID不存在");
        }
        // 更新数据库信息
        databaseDto.setId(databaseId);
        Database database = DatabaseConverter.toEntity(databaseDto);
        JudgeResultJson judgeResultJson = databaseService.add(databaseDto);
        if ("1".equals(judgeResultJson.getCode())) {
            return ResponseUtil.fail("建表失败," + judgeResultJson.getMessage());
        } else {
            List<Problem> problemList = problemService.getByDatabaseId(database.getId());
            if (problemService.updateTrueResult(problemList)) {
                if (databaseService.saveOrUpdate(database)) {
                    return ResponseUtil.ok("更新数据库成功");
                } else {
                    return ResponseUtil.fail("更新数据库失败");
                }
            } else {
                return ResponseUtil.fail("更新题目正确答案失败");
            }
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
