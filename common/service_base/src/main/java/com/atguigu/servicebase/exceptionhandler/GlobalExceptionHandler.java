package com.atguigu.servicebase.exceptionhandler;



import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j//输出为文件
public class GlobalExceptionHandler  {
    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody//为了能够返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理...");
    }
    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理..");
    }
    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody //为了返回数据
    public R error(GuliException e) {
        log.error(e.getMessage());//把异常输出到一个文件
        e.printStackTrace();

        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
