package cn.edu.jmu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@TableName(value = "upload_images")
public class UploadImage implements Serializable {

    private static final long serialVersionUID = 797032287646592913L;

    /**
     * 上传图片ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上传文件名
     */
    @NotBlank
    @TableField(value = "filename")
    private String fileName;

    /**
     * 用户ID
     */
    @TableField(value = "uid")
    private Integer uid;
}
