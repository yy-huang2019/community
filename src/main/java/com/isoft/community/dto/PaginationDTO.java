package com.isoft.community.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {        //包裹里面所要承载的元素
    private List<T> data;    //将所要展示的QuestionDTO对象加入，而后直接在QuestionService中将其setQuestions()进去
    private boolean showPrevious;           //是否展示前一页按钮
    private boolean showFirstPage;          //是否展示第一页按钮
    private boolean showNext;               //是否展示后一页按钮
    private boolean showEndPage;            //是否展示最后一页按钮
    private Integer page;                   //当前页面
    private List<Integer> pages = new ArrayList<>();            //页码数
    private Integer totalPage;              //总页数

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;

        //展示页码数
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //是否展示下一页
        if (totalPage == page) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }

        //是否展示最后一页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
