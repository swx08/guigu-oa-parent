package com.guigu.GlobalExceptionHandler;

import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName:GlobalException
 * Package:com.guigu.GlobalException
 * Description
 *
 * @Author:@wenxueshi
 * @Create:2023/3/5 - 15:04
 * @Version:v1.0
 */

/**
 * 全局异常类
 */
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(GuiguException.class)
    @ResponseBody
    public Result error(GuiguException e){
        e.printStackTrace();
        return Result.fail().message(e.getMessage()).code(e.getCode());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        return Result.fail().code(204).message("无操作权限!");
    }
}
