package com.isoft.community.dto;

import com.isoft.community.exception.CustomizeErrorCode;
import com.isoft.community.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO {                //定义一个返回值的对象，//所有的内容都进行封装传递code跟message
    private int code;
    private String message;

    public static ResultDTO errorOf(Integer code , String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(), ex.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }
}
