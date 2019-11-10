package com.isoft.community.controller;

import com.isoft.community.cache.TagCache;
import com.isoft.community.dto.QuestionDTO;
import com.isoft.community.dto.TagDTO;
import com.isoft.community.model.Question;
import com.isoft.community.model.User;
import com.isoft.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

//    @Autowired
//    private UserMapper userMapper;


    //展示将要跟新的问题(编辑)
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model) {
        QuestionDTO question = questionService.getByID(id);
        model.addAttribute("title", question.getTitle());                   //model将数据保存起来，用于在html页面中显示出来
        model.addAttribute("description", question.getDescription());       //架构该问题的描述拿到
        model.addAttribute("tag", question.getTag());                       //将该问题的标签拿到
        model.addAttribute("id", question.getId());                         //查找id，找到对应的开始问题的id
        model.addAttribute("tags", TagCache.get());                         //加入所需要的标签
        return "publish";
    }



    @GetMapping("/publish")                             //get方式请求
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());                         //加入所需要的标签
        return "publish";
    }

    @PostMapping("/publish")                            //post方式请求用来发布问题信息
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Integer id,
            Model model,
            HttpServletRequest request) {
        model.addAttribute("title", title);                                 //model将数据保存起来，用于在html页面中显示出来
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());                         //加入所需要的标签
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

        String invalid = TagCache.filterIvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签:"+invalid);
            return "publish";
        }

//        User user = null;
//        Cookie[] cookies = request.getCookies();                             //通过cookie方式获取user信息
//        if (cookies != null && cookies.length != 0)                          //判断cookie是否为空
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    String token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    if (user != null) {
//                        request.getSession().setAttribute("user", user);       //request方法拿到session，设置user的信息，登陆成功，写Session和cookie
//                    }
//                    break;
//                }
//            }

        User user = (User) request.getSession().getAttribute("user");         //通过session的getAttribute方法将SessionInterceptor里面得到的session获取到

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setDescription(description);
        question.setTag(tag);
        question.setTitle(title);
        question.setCreator(user.getId());
        question.setId(id);                                 //通过上面的@RequestParam的id属性获得其id值，如果没有问题的话则不会有id值，id值是自动产生的

        questionService.createOrUpdate(question);           //跟新或写入
        //qustionMapper.create(question);                                        //通过set queston中的属性，将其传递给dao层写入数据库
        return "redirect:/index";
    }

}
