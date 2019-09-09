package cn.edu.jmu.judge.entity.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author xeathen
 * @date 2019/9/9 12:18
 */
@Data
public class JudgeResultJson {

    /**
     * 结果代码
     */
    @SerializedName("code")
    public String code;

    /**
     * 判题结果数据
     */
    @SerializedName("data")
    public JudgeResultDataJson data;

    /**
     * 判题结果信息
     */
    @SerializedName("message")
    public String message;
}
