package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.api.problemcategory.CreateProblemCategoryRequest;
import cn.edu.jmu.system.api.problemcategory.CreateProblemCategoryResponse;
import cn.edu.jmu.system.api.problemcategory.DeleteProblemCategoryResponse;
import cn.edu.jmu.system.entity.ProblemCategory;
import cn.edu.jmu.system.entity.dto.ProblemCategoryDto;
import cn.edu.jmu.system.service.ProblemCategoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xeathen
 */
@RestController
@RequiresPermissions(value = {"admin"})
@RequestMapping("/api/admin")
public class ProblemCategoryController {
    @Resource
    ProblemCategoryService problemCategoryService;

    @GetMapping("/problem-category")
    public ResponseEntity<BasicResponse> search(@Validated ProblemCategoryDto problemCategoryDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ProblemCategory> page = new Page<>(pageNum, pageSize);
        IPage<ProblemCategoryDto> iPage = problemCategoryService.search(problemCategoryDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    @PostMapping("/problem-category")
    public ResponseEntity<BasicResponse> create(@RequestBody @Validated CreateProblemCategoryRequest request) {
        CreateProblemCategoryResponse response = problemCategoryService.create(request);
        return ResponseUtil.buildResponse("新增成功", response);
    }

    @DeleteMapping("/problem-category/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id) {
        DeleteProblemCategoryResponse response = problemCategoryService.delete(id);
        return ResponseUtil.buildResponse("删除成功", response);
    }
}
