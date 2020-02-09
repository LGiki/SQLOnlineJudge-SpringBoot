package cn.edu.jmu.system.controller.publics;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.config.UploadImageConfig;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.UploadImage;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.dto.ProblemDetailToUserDto;
import cn.edu.jmu.system.entity.dto.ProblemDto;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.entity.dto.UserDto;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.SolutionService;
import cn.edu.jmu.system.service.UploadImageService;
import cn.edu.jmu.system.service.UserService;
import cn.edu.jmu.system.service.enums.UserStatusEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        ProblemDetailToUserDto detailToUserDto = problemService.getToUserById(id);
        return ResponseUtil.buildResponse("查询成功", detailToUserDto);
    }

    /**
     * Rank榜
     */
    @GetMapping(value = "/rank")
    public ResponseEntity<BasicResponse> rank(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> userPage = new Page<>(pageNum, pageSize);
        UserDto userDto = new UserDto();
        userDto.setStatus(UserStatusEnum.NORMAL);
        IPage<UserDto> iPage = userService.getAll(userDto, userPage, User::getSolved);
        return ResponseUtil.buildResponse("查询成功", iPage);
    }

    /**
     * 查询所有解答
     */
    @GetMapping(value = "/solutions")
    public ResponseEntity<BasicResponse> selectAll(SolutionDto solutionDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Solution> page = new Page<>(pageNum, pageSize);
        IPage<SolutionDto> iPage = solutionService.get(solutionDto, page);
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
