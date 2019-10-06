package com.isoft.community.controller;

import com.isoft.community.mapper.QustionMapper;
import com.isoft.community.mapper.UserMapper;
import com.isoft.community.model.Question;
import com.isoft.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QustionMapper qustionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")                             //get方式请求
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")                            //post方式请求
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            Model model,
            HttpServletRequest request) {
        model.addAttribute("title", title);                                 //model将数据保存起来，用于在html页面中显示出来
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {                                    //判断标题是否为空
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {                        //判断问题补充是否为空
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {                                        //判断所填标签的值是否为空
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = null;
        Cookie[] cookies = request.getCookies();                             //通过cookie方式获取user信息
        if (cookies != null && cookies.length != 0)                          //判断cookie是否为空
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);       //request方法拿到session，设置user的信息，登陆成功，写Session和cookie
                    }
                    break;
                }
            }
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setDescription(description);
        question.setTag(tag);
        question.setTitle(title);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        qustionMapper.create(question);                                        //通过set queston中的属性，将其传递给dao层写入数据库
        return "redirect:/index";
    }

}
