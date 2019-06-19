package cn.edu.jmu.sqlonlinejudge.util;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 基础响应对象，是应用所有的VO对象的父类
 *
 * @author sgh
 * @date 2019/6/18 18:44
 */
@Data
public class BasicResponse<T> {

    /**
     * 消息代码
     * 0-200为正常响应，201-400为错误响应
     */
    private Integer code;

    /**
     * 消息内容
     */
    private String message;

    /**
     * 数据内容
     */
    private T data;

    /**
     * 异常信息
     */
    private String exception;

    public void set(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void set(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setExceptionMessage(Integer code, String exception) {
        this.code = code;
        this.exception = exception;
    }

}