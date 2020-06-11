package cn.edu.jmu.system.controller.publics;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.config.UploadImageConfig;
import cn.edu.jmu.system.entity.*;
import cn.edu.jmu.system.entity.dto.*;
import cn.edu.jmu.system.service.*;
import cn.edu.jmu.system.service.converter.ProblemCategoryConverter;
import cn.edu.jmu.system.service.enums.UserStatusEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jdk.management.resource.internal.ResourceNatives;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

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

    @Resource
    private UploadImageService uploadImageService;

    @Resource
    private ProblemCategoryService problemCategoryService;

    @Resource
    private ProblemCollectionService problemCollectionService;

    /**
     * 查询所有题目
     */
    @GetMapping(value = "/problems")
    public ResponseEntity<BasicResponse> selectAllProblem(ProblemDto problemDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Problem> page = new Page<>(pageNum, pageSize);
        IPage<ProblemDto> iPage = problemService.getAll(problemDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 查询所有题目集
     */
    @GetMapping(value = "/problem-category")
    public ResponseEntity<BasicResponse> selectAllProblemCategory(ProblemCategoryDto problemCategoryDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ProblemCategory> page = new Page<>(pageNum, pageSize);
        IPage<ProblemCategoryDto> iPage = problemCategoryService.search(problemCategoryDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    private interface ProblemCategoryStatusExecutor {
        ResponseEntity<BasicResponse> onStartOrAllowViewAfterEnd();
    }

    private ResponseEntity<BasicResponse> handleProblemCategoryStatus(Integer categoryId, ProblemCategoryStatusExecutor problemCategoryStatusExecutor) {
        ProblemCategory problemCategory = problemCategoryService.getById(categoryId);
        if (problemCategory == null) {
            return ResponseUtil.fail("该ID所对应的题目集不存在！");
        }
        Date currentTime = new Date();
        Date startTime = problemCategory.getStartTime();
        Date endTime = problemCategory.getEndTime();
        if (startTime == null || endTime == null) {
            return ResponseUtil.fail("管理员未设置题目集开始时间或结束时间，请稍后再试！");
        }
        long currentTimestamp = currentTime.getTime();
        long startTimestamp = startTime.getTime();
        long endTimestamp = endTime.getTime();
        if (currentTimestamp < startTimestamp) {
            // 题目集还没开始 -> 返回提示信息
            return ResponseUtil.fail("未到题目集开始时间，请稍后再试！");
        } else {
            // 题目集已经开始
            if (currentTimestamp <= endTimestamp || problemCategory.getViewAfterEnd()) {
                // 题目集还没结束 或 题目集结束后允许查看题目 -> 正常查询
                return problemCategoryStatusExecutor.onStartOrAllowViewAfterEnd();
            } else {
                // 题目集结束且结束后不允许查看题目 -> 返回提示信息
                return ResponseUtil.fail("管理员设置了题目集结束后不能查看题目！");
            }
        }
    }


    /**
     * 通过题目集ID查询题目集包含的题目列表
     */
    @GetMapping(value = "/problem-collection/{id}")
    public ResponseEntity<BasicResponse> selectProblemCategoryDetail(@PathVariable("id") Integer categoryId, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return handleProblemCategoryStatus(categoryId, () -> {
            Page<ProblemCollection> page = new Page<>(pageNum, pageSize);
            ProblemCollectionDto problemCollectionDto = new ProblemCollectionDto();
            problemCollectionDto.setCategoryId(categoryId);
            IPage<ProblemCollectionDto> iPage = problemCollectionService.search(problemCollectionDto, page);
            return ResponseUtil.buildResponse("查询成功", iPage);
        });
    }

    /**
     * @param categoryId 题目集ID
     * @param problemId  题目集中题目的ID
     */
    @GetMapping(value = "/problem/{categoryId}/{problemId}")
    public ResponseEntity<BasicResponse> selectProblemByCategoryIdAndProblemId(@PathVariable("categoryId") Integer categoryId, @PathVariable("problemId") Integer problemId) {
        return handleProblemCategoryStatus(categoryId, () -> {
            // 判断题目ID是否在题目集里，如果在，则查询对应的题目信息，如果不在，则抛出错误
            if (problemCollectionService.isProblemInProblemCollection(problemId, categoryId)) {
                ProblemDetailToUserDto detailToUserDto = problemService.getToUserById(problemId);
                if (detailToUserDto == null) {
                    return ResponseUtil.fail("无法找到该题目或题目对应的数据库信息不存在！");
                } else {
                    return ResponseUtil.buildResponse("查询成功", detailToUserDto);
                }
            } else {
                return ResponseUtil.fail("该题目不在此题目集中！");
            }
        });
    }

    /**
     * 通过题目集ID查询题目集的基础信息
     *
     * @param categoryId 题目集ID
     */
    @GetMapping(value = "/problem-category/{categoryId}")
    public ResponseEntity<BasicResponse> selectProblemCategoryById(@PathVariable("categoryId") Integer categoryId) {
        ProblemCategory problemCategory = problemCategoryService.getById(categoryId);
        if (problemCategory == null) {
            return ResponseUtil.fail("该ID所对应的题目集不存在！");
        }
        ProblemCategoryDto problemCategoryDto = ProblemCategoryConverter.problemCategoryDto(problemCategory);
        return ResponseUtil.buildResponse("查询成功", problemCategoryDto);
    }

//    /**
//     * 得到单个题目的详细信息
//     */
//    @GetMapping(value = "/problems/{id}")
//    public ResponseEntity<BasicResponse> selectProblemById(@PathVariable("id") Integer id) {
//        ProblemDetailToUserDto detailToUserDto = problemService.getToUserById(id);
//        if (detailToUserDto == null) {
//            return ResponseUtil.fail("无法找到该题目或题目对应的数据库信息不存在！");
//        }
//        return ResponseUtil.buildResponse("查询成功", detailToUserDto);
//    }

    /**
     * Rank榜
     */
    @GetMapping(value = "/rank")
    public ResponseEntity<BasicResponse> rank(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> userPage = new Page<>(pageNum, pageSize);
        UserDto userDto = new UserDto();
        userDto.setStatus(UserStatusEnum.NORMAL);
        IPage<UserDto> iPage = userService.getAll(userDto, userPage, User::getSolved, true);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 查询所有解答
     */
    @GetMapping(value = "/solutions")
    public ResponseEntity<BasicResponse> selectAll(SolutionDto solutionDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Solution> page = new Page<>(pageNum, pageSize);
        IPage<SolutionDto> iPage = solutionService.getAll(solutionDto, page);
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

    @GetMapping(value = "/latest_solution")
    public ResponseEntity<BasicResponse> getLatestSolutionByUserIdAndProblemId(@RequestParam Integer uid, @RequestParam Integer pid) {
        String responseMessage = "查询成功";
        Solution solution = null;
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user != null) {
            if (uid.equals(user.getId())) {
                solution = solutionService.getLatestSolutionByUserIdAndProblemId(uid, pid);
                if (solution == null) {
                    responseMessage = "查询失败，找不到记录";
                }
            } else {
                responseMessage = "只能查询自己的记录";
            }
        } else {
            responseMessage = "未登录，无法查询记录";
        }
        return ResponseUtil.buildResponse(responseMessage, solution);
    }

    /**
     * 根据上传图片ID返回图片
     *
     * @param id 上传图片ID
     * @return 图片bytes
     */
    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable String id) {
        UploadImage uploadImage = uploadImageService.getById(id);
        if (uploadImage != null) {
            File uploadDir = new File(UploadImageConfig.DEFAULT_UPLOAD_DIR);
            String imageFilePath = uploadDir.getAbsolutePath() + "/" + uploadImage.getFileName();
            File imageFile = new File(imageFilePath);
            try {
                FileInputStream fileInputStream = new FileInputStream(imageFile);
                byte[] bytes = new byte[fileInputStream.available()];
                int readResult = fileInputStream.read(bytes, 0, fileInputStream.available());
                if (readResult != -1) {
                    return bytes;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
