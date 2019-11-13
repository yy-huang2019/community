package com.isoft.community.controller;


import com.isoft.community.dto.NotificationDTO;
import com.isoft.community.dto.PaginationDTO;
import com.isoft.community.enums.NotificationTypeEnum;
import com.isoft.community.model.User;
import com.isoft.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id,
                          HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");       //通过session的getAttribute方法将SessionInterceptor里面得到的session获取到

        if(user == null){
            return "redirect:/index";
        }

        NotificationDTO notificationDTO =  notificationService.read(id,user);

        if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() ||
          NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuter_id();
        }else {
            return "redirect:/index";
        }
    }
}
