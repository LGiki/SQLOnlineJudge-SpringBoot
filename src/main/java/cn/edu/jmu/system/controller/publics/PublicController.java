package cn.edu.jmu.system.controller.publics;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.config.UploadImageConfig;
import cn.edu.jmu.system.entity.*;
import cn.edu.jmu.system.entity.dto.*;
import cn.edu.jmu.system.service.*;
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
    private UploadImageService uploadImageService;

    @Resource
    private ProblemCategoryService problemCategoryService;

    /**
     * 查询所有题目集
     */
    @GetMapping(value = "/problem-category")
    public ResponseEntity<BasicResponse> selectAllProblemCategory(ProblemCategoryDto problemCategoryDto, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<ProblemCategory> page = new Page<>(pageNum, pageSize);
        IPage<ProblemCategoryDto> iPage = problemCategoryService.search(problemCategoryDto, page);
        return ResponseUtil.buildResponse("查询成功", iPage);
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
