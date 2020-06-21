package cn.edu.jmu.system.controller.handler;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import cn.edu.jmu.system.entity.ProblemCategory;
import cn.edu.jmu.system.service.ProblemCategoryService;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class ProblemCategoryStatusHandler {
    /**
     * 需要判断题目集开始和结束时间时调用的接口
     */
    public interface ProblemCategoryStatusExecutor {
        ResponseEntity<BasicResponse> onAllowView();
    }

    /**
     * 处理题目集的开始结束状态
     *
     * @param problemCategoryId             题目集ID
     * @param problemCategoryStatusExecutor 学生可正常查看题目集时调用的接口
     * @return ResponseEntity<BasicResponse>
     */
    public static ResponseEntity<BasicResponse> handle(Integer problemCategoryId, ProblemCategoryService problemCategoryService, ProblemCategoryStatusExecutor problemCategoryStatusExecutor) {
        ProblemCategory problemCategory = problemCategoryService.getById(problemCategoryId);
        if (problemCategory == null) {
            return ResponseUtil.fail("该ID所对应的题目集不存在！");
        }
        Date currentTime = new Date();
        Date startTime = problemCategory.getStartTime();
        Date endTime = problemCategory.getEndTime();
        if (startTime == null || endTime == null) {
            return ResponseUtil.fail("管理员未设置题目集开始时间或结束时间，请稍后再试！");
        }
        long currentTimestamp = currentTime.getTime();
        long startTimestamp = startTime.getTime();
        long endTimestamp = endTime.getTime();
        if (currentTimestamp < startTimestamp) {
            // 题目集还没开始 -> 返回提示信息
            return ResponseUtil.fail("未到题目集开始时间，请稍后再试！");
        } else {
            // 题目集已经开始
            if (currentTimestamp <= endTimestamp || problemCategory.getViewAfterEnd()) {
                // 题目集还没结束 或 题目集结束后允许查看题目 -> 正常查询
                return problemCategoryStatusExecutor.onAllowView();
            } else {
                // 题目集结束且结束后不允许查看题目 -> 返回提示信息
                return ResponseUtil.fail("管理员设置了题目集结束后不能查看题目！");
            }
        }
    }
}
