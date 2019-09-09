package cn.edu.jmu.judge.entity.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author xeathen
 * @date 2019/9/9 12:18
 */
@Data
public class JudgeResultJson {

    @SerializedName("code")
    public String code;

    @SerializedName("data")
    public JudgeResultDataJson data;

    @SerializedName("message")
    public String message;
}
