package com.guigu.GlobalExceptionHandler;

import com.atguigu.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * ClassName:GuiguException
 * Package:com.guigu.GlobalException
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 15:05
 * @Version:v1.0
 */

/**
 * 自定义异常类
 */
@Data
public class GuiguException extends RuntimeException{
    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param code
     * @param message
     */
    public GuiguException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public GuiguException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
