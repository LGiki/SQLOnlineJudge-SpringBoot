package cn.edu.jmu.judge.util;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 判题工具类
 * @author xeathen
 * @date 2019/9/8 10:47
 */
public class PythonJudgeUtil {


    /**
     * 调用Python脚本，根据solutionId判断Sql语句正确与否
     * @param solutionId
     * @return judgeResultJson
     */
    public static JudgeResultJson sqlJudge(Long solutionId){
        try {
            String[] args = new String[] { "python", "./judger.py", String.valueOf(solutionId)};
            // 执行py文件
            Process proc = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));
            String line = null;
            if ((line = in.readLine()) != null) {
                JudgeResultJson judgeResultJson = new Gson().fromJson(line, JudgeResultJson.class);
                return judgeResultJson;
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
