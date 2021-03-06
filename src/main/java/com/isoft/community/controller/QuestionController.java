package com.isoft.community.controller;

import com.isoft.community.dto.CommentDTO;
import com.isoft.community.dto.QuestionDTO;
import com.isoft.community.enums.CommentTypeEnum;
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

        CommentTypeEnum type = CommentTypeEnum.QUESTION;
        //通过id和类型查找到该问题下面的评论
        List<CommentDTO> comments = commentService.listByTargetId(id , type);

        //通过该问问题的id和tag正则表达式找到相应问题将其展示在相关问题列表内
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);

        //累加阅读数
        questionService.incView(id);

        model.addAttribute("question", questionDTO);                 //通过model将questionDTO的信息全部传递到前端页面
        model.addAttribute("comments", comments);                    //通过model将comments的信息全部传递到前端页面
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
