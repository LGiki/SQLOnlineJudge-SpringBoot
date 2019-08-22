package cn.edu.jmu.common.util;

import cn.edu.jmu.common.enums.ResponseStatusEnum;
import lombok.Data;

/**
 * 基础响应对象
 *
 * @author sgh
 * @date 2019/6/18 18:44
 */
@Data
public class BasicResponse {

    /**
     * 消息代码
     */
    private ResponseStatusEnum responseStatus;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 数据内容
     */
    private Object data;

    public void wrapper(ResponseStatusEnum responseStatus, String message) {
        this.responseStatus = responseStatus;
        this.message = message;
    }

    public void wrapper(ResponseStatusEnum responseStatus, String message, Object data) {
        this.responseStatus = responseStatus;
        this.message = message;
        this.data = data;
    }
}