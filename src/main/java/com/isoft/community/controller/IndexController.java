package com.isoft.community.controller;

import com.isoft.community.dto.QuestionDTO;
import com.isoft.community.mapper.QustionMapper;
import com.isoft.community.mapper.UserMapper;
import com.isoft.community.model.User;
import com.isoft.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QustionMapper qustionMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/index")
    public String Index(HttpServletRequest request,
                        Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);        //request方法拿到session，设置user的信息，登陆成功，写Session和cookie
                    }
                    break;
                }
            }


        List<QuestionDTO> questionList = questionService.list();
            model.addAttribute("question",questionList);

        return "index";
    }
}
