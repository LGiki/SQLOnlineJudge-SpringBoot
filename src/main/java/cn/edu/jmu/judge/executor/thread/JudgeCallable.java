package cn.edu.jmu.judge.executor.thread;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.judge.util.PythonJudgeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author xeathen
 * @date 2019/10/01
 **/
@Slf4j
public class JudgeCallable implements Callable<JudgeResultJson> {

    private Integer solutionId;

    public JudgeCallable(Integer solutionId) {
        this.solutionId = solutionId;
    }

    @Override
    public JudgeResultJson call() {
        try {
            JudgeResultJson json = PythonJudgeUtil.sqlJudge(solutionId);
            log.debug("判题结束");
            log.debug(json.toString());
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getSolutionId() {
        return this.solutionId;
    }
}
