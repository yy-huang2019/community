package com.isoft.community.advice;

import com.alibaba.fastjson.JSONObject;
import com.isoft.community.dto.ResultDTO;
import com.isoft.community.exception.CustomizeErrorCode;
import com.isoft.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//白页处理功能
@ControllerAdvice           //(basePackageClasses = AcmeController.class)如果不声明该扫描的类则扫描所有的类
public class CustomizeExceptionHandle {
    @ExceptionHandler(Exception.class)               //注解处理所有的exception的class
    //该方法返回值返回的是错误页面
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model , HttpServletResponse response) {
        String contentType = request.getContentType();    //得到请求的类型
        if("application/json".equals(contentType)){       //请求是接口的时候直接返回json错误信息
            ResultDTO resultDTO;
            //返回json
            if (ex instanceof CustomizeException) {
                resultDTO =  ResultDTO.errorOf((CustomizeException) ex);           //返回的是错误的json信息
            } else {
                resultDTO =  ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);      //其他的异常信息
            }
            try {
                response.setContentType("application/json");         //直接用servlet方式去获取
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();          //得到响应直接写到前端
                writer.write(JSONObject.toJSONString(resultDTO));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {            //如果请求的接口不是json格式的时候直接返回错误页面
            //返回错误页面
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");     //Controller默认return的是ModelAndView,回传参数为页面，回传error页面

        }

    }
}
