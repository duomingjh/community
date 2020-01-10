package com.luntang.community.community.control;


import com.luntang.community.community.dto.AccessTokenDTO;
import com.luntang.community.community.dto.GithubUser;
import com.luntang.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state){


        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientsecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(clientredirect_uri);
        String token=githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user= githubProvider.getUser(token);
        System.out.println(user.getName());

        return "index";
    }
}
