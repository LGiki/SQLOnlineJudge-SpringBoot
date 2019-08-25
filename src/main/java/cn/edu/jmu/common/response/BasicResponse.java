package cn.edu.jmu.common.response;

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
    private Integer code;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 数据内容
     */
    private Object data;

    public void wrapper(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void wrapper(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}