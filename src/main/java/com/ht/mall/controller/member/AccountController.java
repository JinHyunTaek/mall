package com.ht.mall.controller.member;

import com.ht.mall.form.member.account.LoginForm;
import com.ht.mall.form.member.account.SaveMemberForm;
import com.ht.mall.projections.member.SimpleMember;
import com.ht.mall.service.member.AccountService;
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
@RequestMapping("/member/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/signUp")
    public String signUp(@ModelAttribute("member") SaveMemberForm form) {
        return "member/account/addForm";
    }

    @PostMapping("/signUp")
    public String signUp(
            @Validated @ModelAttribute("member") SaveMemberForm form,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            log.info("signUp error, errors={}",bindingResult);
            return "member/account/addForm";
        }
        accountService.save(form);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("member") LoginForm form){
        return "member/account/loginForm";
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
            return "member/account/loginForm";
        }

        Optional<SimpleMember> member = accountService
                .findByLoginIdAndPassword(form.getLoginId(), form.getPassword());
        if(member.isEmpty()){
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/account/loginForm";
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
