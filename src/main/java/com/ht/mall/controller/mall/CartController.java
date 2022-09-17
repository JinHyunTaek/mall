package com.ht.mall.controller.mall;

import com.ht.mall.service.CartService;
import com.ht.mall.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/mall/cart")
@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

//    @GetMapping("")
//    public String cart(
//            @SessionAttribute(name = "memberId") Long memberId
//    ){
//        cartService.getCartByMember(memberId);
//    }

    @PostMapping("/add")
    public String addItemToCart(
            @SessionAttribute(name = "memberId") Long memberId,
            @RequestParam(name = "itemId") Long itemId
    ){
        cartService.saveItemToCart(memberId, itemId);
//        return "redirect:/mall/cart";
        return "redirect:/mall/main";
    }

}
