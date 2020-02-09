package cn.edu.jmu.judge.util;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 判题工具类
 *
 * @author xeathen
 * @date 2019/9/8 10:47
 */
@Slf4j
public class PythonJudgeUtil {

    /**
     * 调用Python脚本创建数据库
     *
     * @param databaseId
     * @return judgeResultJson
     */
    public static JudgeResultJson createDatabase(Integer databaseId, String createTable, String testData) {
//        String[] args = new String[]{"python", "./judger/createSqlite3Database.py", String.valueOf(databaseId), createTable, testData};
        String[] args = new String[]{"python3", "./judger/createSqlite3Database.py", String.valueOf(databaseId), createTable, testData};
        log.debug("开始创建数据库");
        return pythonAction(args);
    }

    /**
     * 调用Python脚本获取正确答案
     *
     * @param answer
     * @param databaseId
     * @return
     */
    public static JudgeResultJson getTrueResult(String answer, Integer databaseId) {
//        String[] args = new String[]{"python", "./judger/getTrueResultByProblemAnswer.py", answer, String.valueOf(databaseId)};
        String[] args = new String[]{"python3", "./judger/getTrueResultByProblemAnswer.py", answer, String.valueOf(databaseId)};
        log.debug("开始获取正确答案");
        return pythonAction(args);
    }

    /**
     * 调用Python脚本进行判题
     *
     * @param solutionId
     * @return judgeResultJson
     */
    public static JudgeResultJson sqlJudge(Integer solutionId) {
//        String[] args = new String[]{"python", "./judger/judger.py", String.valueOf(solutionId)};
        String[] args = new String[]{"python3", "./judger/judger.py", String.valueOf(solutionId)};
        log.debug("开始判题");
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
