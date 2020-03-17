package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.api.problemcategory.CreateProblemCategoryRequest;
import cn.edu.jmu.system.api.problemcategory.CreateProblemCategoryResponse;
import cn.edu.jmu.system.api.problemcategory.DeleteProblemCategoryResponse;
import cn.edu.jmu.system.entity.ProblemCategory;
import cn.edu.jmu.system.entity.dto.ProblemCategoryDto;
import cn.edu.jmu.system.service.ProblemCategoryService;
import cn.edu.jmu.system.service.converter.ProblemCategoryConverter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xeathen
 */
@RestController
@RequiresPermissions(value = {"admin"})
@RequestMapping("/api/admin/problem-category")
public class ProblemCategoryController {
    @Resource
    ProblemCategoryService problemCategoryService;

    @GetMapping("/")
    public ResponseEntity<BasicResponse> search(ProblemCategoryDto problemCategoryDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ProblemCategory> page = new Page<>(pageNum, pageSize);
        IPage<ProblemCategoryDto> iPage = problemCategoryService.search(problemCategoryDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    @PostMapping("/")
    public ResponseEntity<BasicResponse> create(@RequestBody @Validated CreateProblemCategoryRequest request) {
        CreateProblemCategoryResponse response = problemCategoryService.create(request);
        return ResponseUtil.buildResponse("新增成功", response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> update(@RequestBody @Validated CreateProblemCategoryRequest request, @PathVariable("id") Integer id) {
        ProblemCategory problemCategory = problemCategoryService.getById(id);
        if (problemCategory == null) {
            return ResponseUtil.fail("无此题目集");
        } else {
            problemCategory.setName(request.getName());
            if (problemCategoryService.updateById(problemCategory)) {
                return ResponseUtil.ok("更新题目集信息成功");
            } else {
                return ResponseUtil.fail("更新题目集信息失败");
            }
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> selectProblemCategoryById(@PathVariable("id") Integer id) {
        ProblemCategory problemCategory = problemCategoryService.getById(id);
        if (problemCategory == null) {
            return ResponseUtil.fail("无此题目集！");
        } else {
            ProblemCategoryDto problemCategoryDto = ProblemCategoryConverter.problemCategoryDto(problemCategory);
            return ResponseUtil.buildResponse("查询成功", problemCategoryDto);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id) {
        DeleteProblemCategoryResponse response = problemCategoryService.delete(id);
        return ResponseUtil.buildResponse("删除成功", response);
    }
}
