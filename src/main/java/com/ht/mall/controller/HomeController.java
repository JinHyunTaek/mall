package com.ht.mall.controller;

import com.ht.mall.projections.SimpleMember;
import com.ht.mall.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public String home(
            @SessionAttribute(name = "memberId", required = false) Long memberId,
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
