package com.ht.mall.controller;

import com.ht.mall.form.LoginForm;
import com.ht.mall.form.SaveMemberForm;
import com.ht.mall.projections.SimpleMember;
import com.ht.mall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signUp")
    public String signUp(@ModelAttribute("member") SaveMemberForm form) {
        return "member/addForm";
    }

    @PostMapping("/signUp")
    public String signUp(
            @Validated @ModelAttribute("member") SaveMemberForm form,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            log.info("signUp error, errors={}",bindingResult);
            return "member/addForm";
        }
        memberService.save(form);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("member")LoginForm form){
        return "member/loginForm";
    }

    @PostMapping("/login")
    public String login(
            @Validated @ModelAttribute("member") LoginForm form,
            BindingResult bindingResult,
            HttpServletRequest request,
            @RequestParam(defaultValue = "/") String redirectURL
    ){
        if(bindingResult.hasErrors()){
            log.info("login error, errors={}",bindingResult);
            return "member/loginForm";
        }

        Optional<SimpleMember> member = memberService
                .findByLoginIdAndPassword(form.getLoginId(), form.getPassword());
        if(member.isEmpty()){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute("memberId",member.get().getId());
        return "redirect:"+redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);//가져오되, 생성 x
        if(session!=null){
            session.invalidate();
        }
        return "redirect:/";
    }

}
