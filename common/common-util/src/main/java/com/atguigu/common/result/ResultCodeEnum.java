package com.atguigu.common.result;

import lombok.Getter;

/**
 * ClassName:ResultCodeEnum
 * Package:com.atguigu.common.result
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/4 - 0:56
 * @Version:v1.0
 */

/**
 * 返回数据枚举类
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"成功"),
    FAIL(201,"失败"),
    LOGIN_MOBLE_ERROR(204,"认证失败");

    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
