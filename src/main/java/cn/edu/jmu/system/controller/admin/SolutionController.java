package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.dto.SolutionCodeDto;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.service.SolutionService;
import cn.edu.jmu.system.service.converter.SolutionConverter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sgh
 * @date 2019/08/28 13:34:54
 */
@RestController
@RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
@RequestMapping(value = "/api/admin/solutions")
public class SolutionController {

    @Resource
    private SolutionService solutionService;

    /**
     * 管理员查询全部提交列表
     */
    @GetMapping(value = "/")
    public ResponseEntity<BasicResponse> getList(SolutionDto solutionDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Solution> solutionPage = new Page<>(pageNum, pageSize);
        IPage<SolutionDto> iPage = solutionService.getAll(solutionDto, solutionPage);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 管理员查看提交的源代码
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getCode(@PathVariable(value = "id") Integer id) {
        Solution solution = solutionService.getById(id);
        if (!ObjectUtils.isEmpty(solution)) {
            SolutionCodeDto solutionCodeDto = SolutionConverter.toSolutionCodeDto(solution);
            return ResponseUtil.buildResponse("查询成功", solutionCodeDto);
        } else {
            return ResponseUtil.fail("无此解答");
        }
    }

    /**
     * 查询Solution的数量
     */
    @GetMapping(value = "/count")
    public ResponseEntity<BasicResponse> count() {
        return ResponseUtil.buildResponse(solutionService.count());
    }
}