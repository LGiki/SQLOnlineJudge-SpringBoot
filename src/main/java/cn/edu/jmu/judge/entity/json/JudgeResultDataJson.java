package cn.edu.jmu.judge.entity.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author xeathen
 * @date 2019/9/9 12:22
 */
@Data
public class JudgeResultDataJson {

    @SerializedName("solutionId")
    public String solutionId;

    @SerializedName("result")
    public String result;

    @SerializedName("runError")
    public String runError;

    @SerializedName("trueResult")
    public String trueResult;

}
