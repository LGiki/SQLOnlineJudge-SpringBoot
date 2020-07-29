package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.api.problemcollection.UpdateProblemScoreRequest;
import cn.edu.jmu.system.controller.handler.DeleteInBulkHandler;
import cn.edu.jmu.system.entity.ProblemCollection;
import cn.edu.jmu.system.entity.dto.ProblemCollectionDto;
import cn.edu.jmu.system.service.ProblemCategoryService;
import cn.edu.jmu.system.service.ProblemCollectionService;
import cn.edu.jmu.system.service.ProblemService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author xeathen
 */
@RestController
@RequiresPermissions(value = {"admin"})
@RequestMapping("/api/admin/problem_collection")
public class ProblemCollectionController {
    @Resource
    ProblemCollectionService problemCollectionService;

    @Resource
    ProblemCategoryService problemCategoryService;

    @Resource
    ProblemService problemService;

    @GetMapping("/")
    public ResponseEntity<BasicResponse> search(ProblemCollectionDto problemCollectionDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ProblemCollection> page = new Page<>(pageNum, pageSize);
        IPage<ProblemCollectionDto> iPage = problemCollectionService.search(problemCollectionDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    @PostMapping("/")
    public ResponseEntity<BasicResponse> create(@RequestBody @Validated ProblemCollection problemCollection) {
        problemCollection.setId(null);
        Boolean insertResult = problemCollectionService.save(problemCollection);
        return ResponseUtil.buildResponse(insertResult, "新增题目成功", "新增题目失败");
    }


    /**
     * 通过题目集ID查找题目集所包含的所有题目的ID
     *
     * @param problemCategoryId 题目集ID
     */
    @GetMapping("/problem_ids/{problemCategoryId}")
    public ResponseEntity<BasicResponse> getProblemIdsByProblemCategoryId(@PathVariable("problemCategoryId") Integer problemCategoryId) {
        if (!problemCategoryService.existById(problemCategoryId)) {
            return ResponseUtil.fail("该题目集不存在");
        } else {
            return ResponseUtil.buildResponse("查询成功", problemCollectionService.getProblemIdsByProblemCategoryId(problemCategoryId));
        }
    }


    /**
     * 批量创建ProblemCollection
     *
     * @param problemCategoryId 题目集ID
     * @param problemIds        要添加到题目集中的题目ID
     */
    @PostMapping("/bulk/{problemCategoryId}")
    public ResponseEntity<BasicResponse> createInBulk(@PathVariable("problemCategoryId") Integer problemCategoryId, @RequestBody List<Integer> problemIds) {
        if (problemIds.isEmpty()) {
            return ResponseUtil.fail("请选择要添加到题目集中的题目");
        }
        if (!problemCategoryService.existById(problemCategoryId)) {
            return ResponseUtil.fail("该题目集不存在");
        }
        List<Integer> success = new ArrayList<>();
        List<Integer> fail = new ArrayList<>();
        List<Integer> duplicated = new ArrayList<>();
        ProblemCollection problemCollection = new ProblemCollection();
        problemCollection.setCategoryId(problemCategoryId);
        for (Integer problemId : problemIds) {
            if (problemService.existById(problemId)) {
                if (problemCollectionService.isExistByProblemIdAndProblemCategoryId(problemId, problemCategoryId)) {
                    duplicated.add(problemId);
                } else {
                    problemCollection.setProblemId(problemId);
                    if (problemCollectionService.save(problemCollection)) {
                        success.add(problemId);
                    } else {
                        fail.add(problemId);
                    }
                }
            } else {
                fail.add(problemId);
            }
        }
        HashMap<String, List<Integer>> responseHashMap = new HashMap<>(3);
        responseHashMap.put("success", success);
        responseHashMap.put("duplicated", duplicated);
        responseHashMap.put("fail", fail);
        return ResponseUtil.buildResponse("新增题目完成", responseHashMap);
    }

    /**
     * 批量从从题目集中删除题目
     *
     * @param problemCollectionIds 要删除的ProblemCollection ID
     */
    @DeleteMapping("/bulk")
    public ResponseEntity<BasicResponse> deleteInBulk(@RequestBody List<Integer> problemCollectionIds) {
        return DeleteInBulkHandler.deleteInBulk(problemCollectionService, problemCollectionIds);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicResponse> delete(@PathVariable Integer id) {
        return ResponseUtil.buildResponse(problemCollectionService.delete(id), "删除成功", "删除失败");
    }

    @PutMapping("/update_score/{id}")
    public ResponseEntity<BasicResponse> updateProblemScoreById(@PathVariable Integer id, @RequestBody UpdateProblemScoreRequest updateProblemScoreRequest) {
        boolean updateResult = problemCollectionService.updateProblemScoreById(id, updateProblemScoreRequest.getNewProblemScore());
        return ResponseUtil.buildResponse(updateResult, "修改题目分值成功", "修改题目分值失败，请检查题目集ID是否正确");
    }
}
