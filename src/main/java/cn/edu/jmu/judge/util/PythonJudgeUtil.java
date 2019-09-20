package cn.edu.jmu.judge.util;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 判题工具类
 *
 * @author xeathen
 * @date 2019/9/8 10:47
 */
public class PythonJudgeUtil {

    /**
     * 调用Python脚本创建数据库
     *
     * @param databaseId
     * @return judgeResultJson
     */
    public static JudgeResultJson createDatabase(Integer databaseId) {
        String[] args = new String[]{"python", "./judger/createSqlite3Database.py", String.valueOf(databaseId)};
        return pythonAction(args);
    }

    /**
     * 调用Python脚本获取正确答案
     *
     * @param problemId
     * @return judgeResultJson
     */
    public static JudgeResultJson getTrueResult(Integer problemId) {
        String[] args = new String[]{"python", "./judger/getTrueResultByProblemAnswer.py", String.valueOf(problemId)};
        return pythonAction(args);
    }

    /**
     * 调用Python脚本进行判题
     *
     * @param solutionId
     * @return judgeResultJson
     */
    public static JudgeResultJson sqlJudge(Integer solutionId) {
        String[] args = new String[]{"python", "./judger/judger.py", String.valueOf(solutionId)};
        return pythonAction(args);

    }

    /**
     * 调用Python脚本
     *
     * @param args
     * @return judgeResultJson
     */
    private static JudgeResultJson pythonAction(String[] args) {
        try {
            // 执行py文件
            Process proc = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));
            String line = null;
            if ((line = in.readLine()) != null) {
                return new Gson().fromJson(line, JudgeResultJson.class);
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


}
