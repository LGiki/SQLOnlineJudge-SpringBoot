package cn.edu.jmu.system.controller.admin;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.GenerateUtil;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.common.util.ValidateUtil;
import cn.edu.jmu.system.config.UploadImageConfig;
import cn.edu.jmu.system.entity.Admin;
import cn.edu.jmu.system.entity.UploadImage;
import cn.edu.jmu.system.service.UploadImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequiresRoles(value = {"admin", "teacher"}, logical = Logical.OR)
@RequestMapping("/api/admin/util")
public class UtilController {

    @Resource
    private UploadImageService uploadImageService;

    @PostMapping("/upload")
    public ResponseEntity<BasicResponse> uploadImage(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseUtil.fail("上传失败，请重新选择文件！");
        }
        String fileName = file.getOriginalFilename();
        if (!ValidateUtil.isImage(fileName)) {
            return ResponseUtil.fail("上传失败，请选择正确的图片文件！");
        }
        File uploadDir = new File(UploadImageConfig.DEFAULT_UPLOAD_DIR);
        if (!uploadDir.exists() && !uploadDir.mkdir()) {
            return ResponseUtil.fail("上传失败，系统内部错误，请联系网站管理员！");
        }
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String destFileName = GenerateUtil.getUUID() + "." + extensionName;
        String destFilePath = uploadDir.getAbsolutePath() + "/" + destFileName;
        File dest = new File(destFilePath);
        try {
            file.transferTo(dest);
            Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
            UploadImage uploadImage = new UploadImage();
            uploadImage.setFileName(destFileName);
            uploadImage.setUid(admin.getId());
            uploadImageService.save(uploadImage);
            return ResponseUtil.buildResponse("文件上传成功！", uploadImage);
        } catch (IOException e) {
            log.error("Upload file error: {}", e.getMessage());
        }
        return ResponseUtil.fail("文件上传失败！");
    }
}
