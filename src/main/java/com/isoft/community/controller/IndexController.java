package com.isoft.community.controller;

import com.isoft.community.dto.PaginationDTO;
import com.isoft.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

//    @Autowired
//    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/index")
    public String Index(Model model,
//                      HttpServletRequest request,
                        @RequestParam(name = "page" , defaultValue = "1")Integer page,
                        @RequestParam(name = "size" , defaultValue = "2")Integer size){

//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length != 0)
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    String token = cookie.getValue();
//                    User user = userMapper.findByToken(token);
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);     //request方法拿到session，设置user的信息，登陆成功，写Session和cookie
//                    }
//                    break;
//                }
//            }

        PaginationDTO pagination = questionService.list(page , size);                //Controller层中实例化Service层的list方法获取到PaginationDTO的属性信息
        model.addAttribute("pagination",pagination);                         //model通过addAttribute()方法将得到的键值对传递到前端

        return "index";
    }
}
