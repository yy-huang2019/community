package com.isoft.community.advice;

import com.isoft.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//白页处理功能
@ControllerAdvice           //(basePackageClasses = AcmeController.class)如果不声明该扫描的类则扫描所有的类
public class CustomizeExceptionHandle {
    @ExceptionHandler(Exception.class)               //giant注解处理所有的exception的class
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model) {
        if (ex instanceof CustomizeException) {
            model.addAttribute("message", ex.getMessage());
        } else {
            model.addAttribute("message", "服务冒烟了，要不然你稍后再试试吧！");
        }
        return new ModelAndView("error");     //Controller默认return的是ModelAndView,回传参数为页面，回传error页面
    }
}
