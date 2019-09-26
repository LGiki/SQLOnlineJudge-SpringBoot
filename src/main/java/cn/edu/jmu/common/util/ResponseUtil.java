package cn.edu.jmu.common.util;

import cn.edu.jmu.common.response.BasicResponse;
import org.springframework.http.ResponseEntity;

/**
 * 接口返回工具类
 *
 * @author sgh
 * @date 2019/9/18 16:01
 */

public class ResponseUtil {

    private ResponseUtil() {
    }

    public static ResponseEntity<BasicResponse> buildResponse(Boolean success, String successMessage, String errorMessage) {
        if (success) {
            return ResponseEntity.ok(BasicResponse.ok(successMessage).build());
        } else {
            return ResponseEntity.ok(BasicResponse.fail(errorMessage));
        }
    }

    public static ResponseEntity<BasicResponse> buildResponse(String message, Object data) {
        return ResponseEntity.ok(BasicResponse.ok(message, data));
    }

    public static ResponseEntity<BasicResponse> buildResponse(Object data) {
        return ResponseEntity.ok(BasicResponse.ok(data));
    }

    public static ResponseEntity<BasicResponse> fail(String message) {
        return ResponseEntity.ok(BasicResponse.fail(message));
    }

    public static ResponseEntity<BasicResponse> ok(String message) {
        return ResponseEntity.ok(BasicResponse.ok(message).build());
    }
}

