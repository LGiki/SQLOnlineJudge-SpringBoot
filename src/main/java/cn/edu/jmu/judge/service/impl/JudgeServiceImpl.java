package cn.edu.jmu.judge.service.impl;

import cn.edu.jmu.judge.entity.json.JudgeResultJson;
import cn.edu.jmu.judge.enums.JudgeResponseCodeEnum;
import cn.edu.jmu.judge.executor.ThreadPoolUtils;
import cn.edu.jmu.judge.executor.thread.JudgeCallable;
import cn.edu.jmu.judge.service.JudgeService;
import cn.edu.jmu.judge.util.Md5Util;
import cn.edu.jmu.judge.util.PythonJudgeUtil;
import cn.edu.jmu.system.entity.Problem;
import cn.edu.jmu.system.entity.Solution;
import cn.edu.jmu.system.entity.User;
import cn.edu.jmu.system.entity.UserProblem;
import cn.edu.jmu.system.entity.dto.SolutionDto;
import cn.edu.jmu.system.mapper.SolutionMapper;
import cn.edu.jmu.system.service.ProblemService;
import cn.edu.jmu.system.service.UserProblemService;
import cn.edu.jmu.system.service.UserService;
import cn.edu.jmu.system.service.enums.SolutionResultEnum;
import cn.edu.jmu.system.service.impl.UserProblemServiceImpl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.FutureTask;

/**
 * @author xeathen
 * @date 2019/9/7 16:45
 */
@Service
public class JudgeServiceImpl extends ServiceImpl<SolutionMapper, Solution> implements JudgeService {


    @Resource
    private UserService userService;


    @Resource
    private UserProblemService userProblemService;

    @Resource
    private ProblemService problemService;


    /**
     * 判题
     *
     * @param solutionDto
     * @return boolean
     */
    @Override
    public boolean judge(SolutionDto solutionDto) {
        //调用线程池判题
        log.debug("solutionDtoId: " + solutionDto.getId().toString());
        JudgeResultJson result = executeTask(solutionDto.getId());
        //判题脚本结束
        Solution solution = cn.edu.jmu.system.service.mapper.SolutionMapper.toEntity(solutionDto);
        if (result != null) {
            String code = result.getCode();
            String resultCode = result.getData().getResult();
            String runError = result.getData().getRunError();
            //检查UserProblem表，插入记录
            Integer uid = solution.getUid();
            Integer pid = solution.getPid();
            UserProblem userProblem = new UserProblem();
            userProblem.setUid(uid);
            userProblem.setPid(pid);
            User user = userService.getById(uid);
            Problem problem = problemService.getById(pid);
            Integer userProblemId = userProblemService.find(uid, pid);
            if (JudgeResponseCodeEnum.OK.getValue().equals(code)) {
                if (SolutionResultEnum.ACCEPTED.getValue().equals(resultCode)) {
                    solution.setResult(SolutionResultEnum.ACCEPTED);
                    if (userProblemService.find(uid, pid, true) == 0) {
                        increaseSolvedCount(user, problem);
                    }
                    if (userProblemId != 0) {
                        userProblem.setId(userProblemId);
                    }
                    userProblem.setState(true);
                } else {
                    if (SolutionResultEnum.COMPILE_ERROR.getValue().equals(resultCode)) {
                        solution.setResult(SolutionResultEnum.COMPILE_ERROR);
                    } else if (SolutionResultEnum.WRONG_ANSWER.getValue().equals(resultCode)) {
                        solution.setResult(SolutionResultEnum.WRONG_ANSWER);
                    } else {
                        solution.setResult(SolutionResultEnum.UNKNOWN);
                    }
                    if (runError != null) {
                        solution.setRunError(runError);
                    }
                    if (userProblemId == 0) {
                        userProblem.setState(false);
                    } else {
                        userProblem.setId(userProblemId);
                    }
                }
                increaseSubmitCount(user, problem);
            } else if (JudgeResponseCodeEnum.FAIL.getValue().equals(code)) {
                baseMapper.deleteById(solution.getId());
                return false;
            }
            userProblemService.saveOrUpdate(userProblem);
            baseMapper.updateById(solution);
            userService.saveOrUpdate(user);
            return true;
        } else {
            baseMapper.deleteById(solution.getId());
            return false;
        }
    }

    /**
     * 获取正确答案
     *
     * @param answer
     * @param databaseId
     * @return boolean
     */
    @Override
    public String getTrueResult(String answer, Integer databaseId) {
        JudgeResultJson judgeResultJson = PythonJudgeUtil.getTrueResult(answer, databaseId);
        log.debug(judgeResultJson.toString());
        if (JudgeResponseCodeEnum.OK.getValue().equals(judgeResultJson.getCode())) {
            return judgeResultJson.getData().getTrueResult();
        }
        return null;
    }

    /**
     * 获取正确答案的Md5值
     *
     * @param answer
     * @param databaseId
     * @return boolean
     */
    @Override
    public String getTrueResultMd5(String answer, Integer databaseId) {
        JudgeResultJson judgeResultJson = PythonJudgeUtil.getTrueResult(answer, databaseId);
        log.debug(judgeResultJson.toString());
        if (JudgeResponseCodeEnum.OK.getValue().equals(judgeResultJson.getCode())) {
            return Md5Util.getStringMd5(judgeResultJson.getData().getTrueResult());
        }
        return null;
    }


    private void increaseSubmitCount(User user, Problem problem) {
        user.setSubmit(user.getSubmit() + 1);
        problem.setSubmit(problem.getSubmit() + 1);
    }

    private void increaseSolvedCount(User user, Problem problem) {
        user.setSolved(user.getSolved() + 1);
        problem.setSolved(problem.getSolved() + 1);
    }

    private JudgeResultJson executeTask(Integer solutionId) {
        JudgeResultJson result = null;
        try {
            FutureTask<JudgeResultJson> futureTask = new FutureTask<>(new JudgeCallable(solutionId));
            ThreadPoolUtils.getInstance().submit(futureTask);
            result = futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
