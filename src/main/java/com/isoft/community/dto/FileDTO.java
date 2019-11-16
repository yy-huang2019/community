package com.isoft.community.dto;

import lombok.Data;

@Data
public class FileDTO {                       //文件上传
    private int success;
    private String url;
    private String message;
}
