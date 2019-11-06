package com.isoft.community.controller;

import com.isoft.community.dto.CommentCreateDTO;
import com.isoft.community.dto.CommentDTO;
import com.isoft.community.dto.QuestionDTO;
import com.isoft.community.service.CommentService;
import com.isoft.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    //问题回复
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getByID(id);          //通过service将数据拿到

        //
        List<CommentDTO> comments = commentService.listByQuestionId(id);

        //累加阅读数
        questionService.incView(id);

        model.addAttribute("question", questionDTO);                 //通过model将questionDTO的信息全部传递到前端页面
        model.addAttribute("comments", comments);                    //通过model将comments的信息全部传递到前端页面
        return "question";
    }
}
