package com.ht.mall.controller;

import com.ht.mall.argumentresolver.Login;
import com.ht.mall.projections.member.SimpleMember;
import com.ht.mall.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public String home(
            @Login Long memberId,
            Model model
    ){
        if(memberId != null){
            SimpleMember member = homeService.findById(memberId);
            model.addAttribute("member",member);
            return "loginHome";
        }
        return "home";
    }


}
