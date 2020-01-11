package com.luntang.community.community.control;


import com.luntang.community.community.dto.AccessTokenDTO;
import com.luntang.community.community.dto.GithubUser;
import com.luntang.community.community.mapper.UserMapper;
import com.luntang.community.community.model.User;
import com.luntang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientsecret;
    @Value("${github.client.redirect_uri}")
    private String clientredirect_uri;

   @Autowired
    private UserMapper userMapper;



    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request, HttpServletResponse httpServletResponse){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientsecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientredirect_uri);
        String accesstoken=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser= githubProvider.getUser(accesstoken);
        if(githubUser!=null){
           User user1=new User();
            String token=UUID.randomUUID().toString();
            user1.setToken(token);
            user1.setName(githubUser.getName());
            user1.setAccountId(String.valueOf(githubUser.getId()));
            user1.setGmtCreate(System.currentTimeMillis());
            user1.setGmtModified(user1.getGmtCreate());
            userMapper.insert(user1);
            httpServletResponse.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else {
            return "redirect:/";
        }


    }
}
