package com.isoft.community.enums;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()){
            if(commentTypeEnum.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    private Integer type;

    CommentTypeEnum(int type) {
        this.type = type;
    }
}
