package com.isoft.community.controller;

import com.isoft.community.dto.AccessTokenDTO;
import com.isoft.community.dto.GitHubUser;
import com.isoft.community.mapper.UserMapper;
import com.isoft.community.model.User;
import com.isoft.community.provider.GitHubProvider;
import com.isoft.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
@Slf4j       //lombook自动将log注入
public class AuthorizeController {
    @Autowired                                        //自动注入
    private GitHubProvider gitHubProvider;
    @Autowired
    private UserService userService;

    @Value("${Github.client.id}")                    //通过@Value注解将properties中的参数注入到需要的变量中
    private String clientID;
    @Value("${Github.client.secret}")
    private String client_secret;
    @Value("${Github.Redirect.uri}")
    private String Redirect_uri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")                       //虚拟路径，@RequestMapping(method = RequestMethod.GET)的缩写，该注解将HTTP Get 映射到 特定的处理方法上。
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){                               //session通过Requset方法拿到
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(Redirect_uri);                                  //传递accessTokenDTO的五个变量值
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);            //获取access_token中的值
        GitHubUser gitHubUser = null;                                                  //将access_token传入返回得到的user信息
        try {
            gitHubUser = gitHubProvider.getUser(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
         if(gitHubUser != null && gitHubUser.getName() != null){
             User user = new User();
             user.setToken(UUID.randomUUID().toString());
             String token = user.getToken();
             user.setName(gitHubUser.getName());
             String name = user.getName();
             user.setAccount_id(String.valueOf(gitHubUser.getId()));                    //String.valueOf()进行强制转化
             String account_id = user.getAccount_id();
             user.setAvatar_url(gitHubUser.getAvatar_url());
             String avatar_url = user.getAvatar_url();

             userService.createOrUpdate(user);
             //userMapper.insert(name ,account_id ,token ,gmt_create ,gmt_modified ,avatar_url);     //调用insert方法

             response.addCookie(new Cookie("token" , token));                    //将生成的token写入cookie
             return "redirect:/index";                                                      //redirect重定向到index页面
         }else {
             log.error("callback get github error,{}",gitHubUser);
             return "redirect:/index";                                                      //登录失败，重新登录
         }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,                   //session通过request设置
                         HttpServletResponse response) {               //cookie通过response设置
        request.getSession().removeAttribute("user");               //过去登录的user的session并进行移除
        Cookie cookie = new Cookie("token", null);        //通过token设置新的cookie为null值
        cookie.setMaxAge(0);                                          //将cookie的该属性设置为0
        response.addCookie(cookie);                                   //添加该cookie即可删除原有的cookie
        return "redirect:/index";
    }

}