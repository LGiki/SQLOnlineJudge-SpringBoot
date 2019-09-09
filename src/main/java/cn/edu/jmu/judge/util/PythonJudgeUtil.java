package cn.edu.jmu.judge.util;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author xeathen
 * @date 2019/9/8 10:47
 */
public class PythonJudgeUtil {


    public static JudgeResultJson sqlJudge(Long solutionID){
        try {
            String[] args = new String[] { "python", "./judger.py", String.valueOf(solutionID)};
            // 执行py文件
            Process proc = Runtime.getRuntime().exec(args);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));
            String line = null;
            if ((line = in.readLine()) != null) {
                return new Gson().fromJson(line, JudgeResultJson.class);
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
