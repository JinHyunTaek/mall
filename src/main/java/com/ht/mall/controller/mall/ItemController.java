package com.ht.mall.controller.mall;

import com.ht.mall.argumentresolver.Login;
import com.ht.mall.entity.enumType.ItemCategory;
import com.ht.mall.entity.Member;
import com.ht.mall.entity.enumType.MemberLevel;
import com.ht.mall.exeption.BasicException;
import com.ht.mall.form.item.ItemDetailForm;
import com.ht.mall.form.item.SaveItemForm;
import com.ht.mall.repository.member.MemberRepository;
import com.ht.mall.service.mall.ItemImageService;
import com.ht.mall.service.mall.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

import static com.ht.mall.exeption.ErrorCode.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/mall/item")
public class ItemController {

    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final MemberRepository memberRepository;

    @GetMapping("/save")
    public String save(@ModelAttribute("item")SaveItemForm form,
                       @Login Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BasicException(NO_MEMBER_FOUND));

        if(member.getMemberLevel().equals(MemberLevel.CUSTOMER)){
            log.info("call forbidden error");
            throw new BasicException(FORBIDDEN);
        }

        form.setItemCategories(List.of(ItemCategory.values()));
        return "mall/item/addForm";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("item") SaveItemForm form,
                       BindingResult bindingResult,
                       @SessionAttribute("memberId") Long memberId){
        if(bindingResult.hasErrors()){
            log.info("item save error, errors={}",bindingResult);
            return "mall/item/addForm";
        }
        itemService.saveItem(form,memberId);

        return "redirect:/mall/main";
    }

    @GetMapping("/detail/{itemId}")
    public String detail(
            @PathVariable("itemId") Long itemId,
            @SessionAttribute(name = "memberId", required = false) Long memberId,
            ItemDetailForm form,
            Model model
    ){
        ItemDetailForm item = itemService.detail(form, itemId);
        model.addAttribute("item",item);
        model.addAttribute("memberId",memberId);
        return "mall/item/detail";
    }


    @ResponseBody
    @GetMapping("/image/{representImageName}")
    public Resource downloadFile(@PathVariable String representImageName) throws MalformedURLException {
        return new UrlResource("file:" + itemImageService.getFullPath(representImageName));
    }

    @PostMapping("/delete/{itemId}")
    public String delete(
            @PathVariable("itemId") Long itemId,
            @SessionAttribute(name = "memberId") Long memberId
    ){
        if(memberId==null){
            throw new BasicException(UNAUTHORIZED);
        }
        itemService.delete(itemId);
        return "redirect:/mall/main";
    }

    @ResponseBody
    @PostMapping("/like")
    public void saveLike(
            @RequestParam("itemId") Long itemId,
            @RequestParam("memberId") Long memberId
    ){
        itemService.saveOrDeleteLike(itemId, memberId);
    }

}
