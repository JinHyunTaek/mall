package com.ht.mall.controller.member;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.OrderItemSimpleDto;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.exeption.ErrorCode;
import com.ht.mall.form.member.MyProfileForm;
import com.ht.mall.form.member.SimpleProfileForm;
import com.ht.mall.form.member.MemberUpdateForm;
import com.ht.mall.service.member.MemberService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

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
        Page<OrderItemSimpleDto> orderItems = memberService.findOrderItem(pageable, itemCond);
        MyProfileForm member = memberService.setMyProfile(myProfileForm, memberId);

        model.addAttribute("orderItems",orderItems);
        model.addAttribute("member",member);
        return "member/myProfile";
    }

    @GetMapping("/update")
    public String update(
            @SessionAttribute("memberId") Long memberId,
            MemberUpdateForm form,
            Model model
    ){
        MemberUpdateForm member = memberService.setUpdateForm(memberId, form);
        model.addAttribute("member",member);
        return "member/updateForm";
    }

    @PostMapping("/update")
    public String update(
            @Validated @ModelAttribute("member") MemberUpdateForm form,
            BindingResult bindingResult,
            Model model
    ){
        if(bindingResult.hasErrors()){
            log.error("errors={}",bindingResult);
            return "member/updateForm";
        }

        memberService.update(form);
        return "redirect:/member/myProfile";
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
