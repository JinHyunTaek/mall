package com.ht.mall.controller.mall;

import com.ht.mall.service.mall.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mall/main")
public class MainController {

    private final MainService mainService;

    @GetMapping("")
    public String main(Model model){
        mainService.setMallMain(model);
        return "mall/main";
    }

}
