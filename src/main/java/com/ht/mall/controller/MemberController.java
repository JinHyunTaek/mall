package com.ht.mall.controller;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.ItemSimpleDto;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.exeption.ErrorCode;
import com.ht.mall.form.member.LoginForm;
import com.ht.mall.form.member.MyProfileForm;
import com.ht.mall.form.member.SaveMemberForm;
import com.ht.mall.form.member.SimpleProfileForm;
import com.ht.mall.projections.member.SimpleMember;
import com.ht.mall.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

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

    @GetMapping("/myProfile")
    public String myProfile(
            @SessionAttribute("memberId") Long memberId,
            @PageableDefault(sort = "id",direction = DESC) Pageable pageable,
            PageItemCond itemCond,
            MyProfileForm myProfileForm,
            Model model
    ){
        if(memberId == null){
            throw new BasicException(ErrorCode.BAD_REQUEST);
        }

        itemCond.setMemberId(memberId);
        Page<ItemSimpleDto> items = memberService.findOrderItem(pageable, itemCond);
        MyProfileForm member = memberService.setMyProfile(myProfileForm, memberId);

        model.addAttribute("items",items);
        model.addAttribute("member",member);
        return "member/myProfile";
    }

    @GetMapping("/profile")
    public String profile(Long memberId){
        return "member/profile";
    }

    @GetMapping("/simpleProfile")
    public String simpleProfile(@RequestParam Long memberId,
                                SimpleProfileForm form, Model model){
        SimpleProfileForm profile = memberService.findSimpleProfileByMemberId(form, memberId);
        model.addAttribute("member",profile);
        return "member/simpleProfile";
    }

}
