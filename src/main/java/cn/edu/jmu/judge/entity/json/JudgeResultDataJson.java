package cn.edu.jmu.judge.entity.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author xeathen
 * @date 2019/9/9 12:22
 */
@Data
public class JudgeResultDataJson {

    /**
     * 解答ID
     */
    @SerializedName("solutionId")
    public String solutionId;

    /**
     * 结果
     */
    @SerializedName("result")
    public String result;

    /**
     * 运行错误
     */
    @SerializedName("runError")
    public String runError;


    /**
     * 正确结果
     */
    @SerializedName("trueResult")
    public String trueResult;

}
