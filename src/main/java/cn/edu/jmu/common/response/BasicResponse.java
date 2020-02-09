package cn.edu.jmu.common.response;

import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * 基础响应对象
 *
 * @author sgh
 * @date 2019/6/18 18:44
 */
@Data
public class BasicResponse<T> {

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
    private T data;

    public void wrapper(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void wrapper(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BasicResponse(Integer code, String message) {
        this(code, message, null);
    }

    public BasicResponse(Integer code, String message, @Nullable T data) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static BasicResponse.BodyBuilder status(Integer status, String message) {
        return new BasicResponse.DefaultBuilder(status, message);
    }

    public static BasicResponse fail() {
        BasicResponse.BodyBuilder builder = status(1, "操作失败");
        return builder.build();
    }

    public static BasicResponse fail(String message) {
        BasicResponse.BodyBuilder builder = status(1, message);
        return builder.build();
    }

    public static BasicResponse.BodyBuilder ok() {
        return status(0, "操作成功");
    }

    public static BasicResponse.BodyBuilder ok(String message) {
        return status(0, message);
    }

    public static <T> BasicResponse<T> ok(String message, T body) {
        BasicResponse.BodyBuilder builder = ok(message);
        return builder.body(body);
    }

    public static <T> BasicResponse<T> ok(T body) {
        BasicResponse.BodyBuilder builder = ok();
        return builder.body(body);
    }

    private static class DefaultBuilder implements BasicResponse.BodyBuilder {
        private final Integer statusCode;
        private final String message;

        private DefaultBuilder(Integer statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }

        @Override
        public <T> BasicResponse<T> body(@Nullable T body) {
            return new BasicResponse<>(this.statusCode, this.message, body);
        }

        @Override
        public <T> BasicResponse<T> build() {
            return this.body(null);
        }
    }

    public interface BodyBuilder {

        /**
         * 构建数据
         *
         * @param var1 var1
         * @param <T>  <T>
         * @return BasicResponse
         */
        <T> BasicResponse<T> body(@Nullable T var1);

        /**
         * 构建
         *
         * @return BasicResponse
         */
        <T> BasicResponse<T> build();
    }
}