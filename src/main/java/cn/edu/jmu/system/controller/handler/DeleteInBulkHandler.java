package cn.edu.jmu.system.controller.handler;

import cn.edu.jmu.common.response.BasicResponse;
import cn.edu.jmu.common.util.ResponseUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeleteInBulkHandler {
    public static ResponseEntity<BasicResponse> deleteInBulk(IService iService, List<Integer> ids) {
        if (ids.isEmpty()) {
            return ResponseUtil.fail("删除列表为空");
        }
        List<Integer> success = new ArrayList<>();
        List<Integer> fail = new ArrayList<>();
        for (Integer id : ids) {
            if (iService.removeById(id)) {
                success.add(id);
            } else {
                fail.add(id);
            }
        }
        HashMap<String, List<Integer>> responseHashMap = new HashMap<>(2);
        responseHashMap.put("success", success);
        responseHashMap.put("fail", fail);
        return ResponseUtil.buildResponse("删除完成", responseHashMap);
    }
}
