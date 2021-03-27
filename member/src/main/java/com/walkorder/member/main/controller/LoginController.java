package com.walkorder.member.main.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.walkorder.member.main.service.KakaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class LoginController {

    final String RESTAPI_KEY = "0e5bc43cde12fc5035c512eca57aa8be";
    final String REDIRECT_URI = "http://localhost:8080/kakao/callback";

    @Autowired
    private KakaoService kakaoservice;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "member/login";
    }

    @RequestMapping(value = "/kakao", method = RequestMethod.GET)
    public String kakaoConnect() {

        StringBuffer url = new StringBuffer();

        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id=" + RESTAPI_KEY);
        url.append("&redirect_uri=" + REDIRECT_URI);
        url.append("&response_type=code");

        return "redirect:" + url.toString();
    }

    @RequestMapping(value = "/kakao/callback", produces = "application/json", method = { RequestMethod.GET,
            RequestMethod.POST })
    public String kakaoLogin(@RequestParam("code") String code, RedirectAttributes ra, HttpSession session,
            HttpServletResponse response) throws IOException {
        // http://localhost:8080/kakao/callback?code=RTPCN_ZskA6OY87e5i1D80olEgRWEWxEu-vPkLEbvJL6OEBcMi5w7VC5Lqvp6SxB_yfLXAopb1UAAAF4clS7QA

        log.info("kakao code : ", code);
        String accessToken = kakaoservice.getAccessTOken(code, RESTAPI_KEY);

        return "index";
    }

}
