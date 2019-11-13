package com.isoft.community.controller;

import com.isoft.community.dto.PaginationDTO;
import com.isoft.community.mapper.UserMapper;
import com.isoft.community.model.Notification;
import com.isoft.community.model.User;
import com.isoft.community.service.NotificationService;
import com.isoft.community.service.QuestionService;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,                 //@PathVariable注解可以动态路由
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "2") Integer size,
                          Model model) {

//        User user = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null && cookies.length != 0){
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    String token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);     //request方法拿到session，设置user的信息，登陆成功，写Session和cookie
//                    }
//                    break;
//                }
//            }
//        }

        User user = (User) request.getSession().getAttribute("user");       //通过session的getAttribute方法将SessionInterceptor里面得到的session获取到

        if(user == null){
            return "redirect:/index";
        }

        if("questions".equals(action)){                                       //action的值通过profile页面传递过来的参数确定
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");

            PaginationDTO paginationDTO =  questionService.list(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        }else if("replies".equals(action)){

            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }


        return "profile";
    }


}
