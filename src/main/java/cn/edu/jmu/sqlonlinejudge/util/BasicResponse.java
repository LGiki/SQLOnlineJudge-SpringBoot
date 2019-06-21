package cn.edu.jmu.sqlonlinejudge.util;

import lombok.Data;

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
     * 2xx为正常响应，4xx为错误响应，5xx为异常响应
     * 200 -> 正常
     * 400 -> 错误
     * 503 -> 异常
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

    public void set(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void set(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}