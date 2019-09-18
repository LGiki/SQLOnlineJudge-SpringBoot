package cn.edu.jmu.system.controller.publics;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.dto.ProblemDetailToUserDto;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.entity.dto.UserDto;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.SolutionService;
import cn.edu.jmu.system.service.UserService;
import cn.edu.jmu.system.service.enums.UserStatusEnum;
import cn.edu.jmu.system.service.mapper.ProblemMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author sgh
 * @date 2019/8/26 下午2:13
 */
@RestController
@RequestMapping(value = "/api/public")
public class PublicController {

    @Resource
    private ProblemService problemService;

    @Resource
    private UserService userService;

    @Resource
    private SolutionService solutionService;

    /**
     * 查询所有题目
     */
    @GetMapping(value = "/problems")
    public ResponseEntity<BasicResponse> selectAll(ProblemDto problemDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Problem> page = new Page<>(pageNum, pageSize);
        IPage<ProblemDto> iPage = problemService.getAll(problemDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 得到单个题目的详细信息
     */
    @GetMapping(value = "/problems/{id}")
    public ResponseEntity<BasicResponse> selectById(@PathVariable("id") Integer id) {
        Problem problem = problemService.getById(id);
        ProblemDetailToUserDto problemDetailToUserDto = ProblemMapper.toDetailDto(problem);
        return ResponseUtil.buildResponse("查询成功", problemDetailToUserDto);
    }

    /**
     * Rank榜
     */
    @GetMapping(value = "/rank")
    public ResponseEntity<BasicResponse> rank(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> userPage = new Page<>(pageNum, pageSize);
        UserDto userDto = new UserDto();
        userDto.setStatus(UserStatusEnum.NORMAL);
        IPage<UserDto> iPage = userService.getAll(userDto, userPage);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 通过ID查询解答详情
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasicResponse> getSolutionById(@PathVariable("id") Integer id) {
        Solution solution = solutionService.getById(id);
        return ResponseUtil.buildResponse("查询成功", solution);
    }

    /**
     * 查询所有解答
     */
    @GetMapping(value = "/solutions")
    public ResponseEntity<BasicResponse> selectAll(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Solution> page = new Page<>(pageNum, pageSize);
        IPage<SolutionDto> iPage = solutionService.get(null, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 获取解答数量
     */
    @GetMapping(value = "/solutions/count")
    public ResponseEntity<BasicResponse> count() {
        int count = solutionService.count();
        return ResponseUtil.buildResponse(count);
    }
}
