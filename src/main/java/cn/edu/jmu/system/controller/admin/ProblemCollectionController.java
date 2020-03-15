package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.api.problemcollection.CreateProblemCollectionRequest;
import cn.edu.jmu.system.api.problemcollection.CreateProblemCollectionResponse;
import cn.edu.jmu.system.api.problemcollection.DeleteProblemCollectionResponse;
import cn.edu.jmu.system.entity.ProblemCollection;
import cn.edu.jmu.system.entity.dto.ProblemCollectionDto;
import cn.edu.jmu.system.service.ProblemCollectionService;
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
public class ProblemCollectionController {
    @Resource
    ProblemCollectionService problemCollectionService;

    @GetMapping("/problem-collection")
    public ResponseEntity<BasicResponse> search(@Validated ProblemCollectionDto problemCollectionDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ProblemCollection> page = new Page<>(pageNum, pageSize);
        IPage<ProblemCollectionDto> iPage = problemCollectionService.search(problemCollectionDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    @PostMapping("/problem-collection")
    public ResponseEntity<BasicResponse> create(@RequestBody @Validated CreateProblemCollectionRequest request) {
        CreateProblemCollectionResponse response = problemCollectionService.create(request);
        return ResponseUtil.buildResponse("新增成功", response);
    }

    @DeleteMapping("/problem-collection/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id) {
        DeleteProblemCollectionResponse response = problemCollectionService.delete(id);
        return ResponseUtil.buildResponse("删除成功", response);
    }
}
