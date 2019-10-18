package com.isoft.community.controller;

import com.isoft.community.dto.QuestionDTO;
import com.isoft.community.mapper.QustionMapper;
import com.isoft.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    //问题回复
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getByID(id);          //通过service将数据拿到
        model.addAttribute("question", questionDTO);                 //通过model将questionDTO的信息全部传递到前端页面
        return "question";
    }
}
