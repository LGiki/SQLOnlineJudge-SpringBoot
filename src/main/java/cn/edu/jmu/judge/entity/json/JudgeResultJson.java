package cn.edu.jmu.judge.entity.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xeathen
 * @date 2019/9/9 12:18
 */
@Data
@ToString
public class JudgeResultJson implements Serializable {

    private static final long serialVersionUID = 8788508995412552674L;
    /**
     * 结果代码
     */
    @SerializedName("code")
    private String code;

    /**
     * 判题结果数据
     */
    @SerializedName("data")
    private JudgeResultDataJson data;

    /**
     * 判题结果信息
     */
    @SerializedName("message")
    private String message;
}
