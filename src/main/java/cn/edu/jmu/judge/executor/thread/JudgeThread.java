package cn.edu.jmu.judge.executor.thread;

import cn.edu.jmu.judge.util.PythonJudgeUtil;

/**
 * @author xeathen
 * @date 2019/10/01
 **/
public class JudgeThread implements Runnable {


    private Integer solutionId;

    public JudgeThread(Integer solutionId){
        this.solutionId = solutionId;
    }

    @Override
    public void run() {
        PythonJudgeUtil.sqlJudge(solutionId);
    }
}
