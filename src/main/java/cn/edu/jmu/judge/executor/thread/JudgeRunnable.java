package cn.edu.jmu.judge.executor.thread;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.judge.util.PythonJudgeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xeathen
 * @date 2019/10/5 15:59
 */
@Slf4j
public class JudgeRunnable implements Runnable {

    private Integer solutionId;

    public JudgeRunnable(Integer solutionId) {
        this.solutionId = solutionId;
    }

    @Override
    public void run() {
        try {
            JudgeResultJson json = PythonJudgeUtil.sqlJudge(solutionId);
            log.debug("判题结束");
            log.debug(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getSolutionId() {
        return this.solutionId;
    }
}
