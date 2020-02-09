package cn.edu.jmu.judge.entity.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xeathen
 * @date 2019/9/9 12:22
 */
@Data
public class JudgeResultDataJson implements Serializable {

    private static final long serialVersionUID = 4535318128017386805L;
    /**
     * 解答ID
     */
    @SerializedName("solutionId")
    private String solutionId;

    /**
     * 结果
     */
    @SerializedName("result")
    private String result;

    /**
     * 运行错误
     */
    @SerializedName("runError")
    private String runError;

    /**
     * 正确结果
     */
    @SerializedName("trueResult")
    private String trueResult;
}
