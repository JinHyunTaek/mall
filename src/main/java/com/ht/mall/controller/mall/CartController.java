package com.ht.mall.controller.mall;

import com.ht.mall.form.cart.CartForm;
import com.ht.mall.service.mall.CartService;
import com.ht.mall.service.mall.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/mall/cart")
@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping("")
    public String cart(
            @SessionAttribute(name = "memberId") Long memberId,
            Model model
    ){
        CartForm cartForm = cartService.getCartFormByMember(memberId);
        System.out.println("cartFormList = " + cartForm.getCartFormList());
        model.addAttribute("cart",cartForm);
        return "mall/cart/cart";
    }

    @PostMapping("/order")
    public String cart(
            @Validated @ModelAttribute("cart") CartForm cartForm,
            BindingResult bindingResult,
            @RequestParam(name = "checkedCartItemIds",required = false) List<String> checkedCartItemIds,
            @SessionAttribute("memberId") Long memberId,
            Model model
    ){
        System.out.println("cartForm = " + cartForm);
        if(checkedCartItemIds ==null){
            bindingResult.reject("checkbox","You need to select at least one checkbox");
            log.info("checkbox unselected");
            return "mall/cart/cart";
        }
        if(bindingResult.hasErrors()){
            bindingResult.reject("error","quantity input error [min:1, max:999]");
            log.info("cart order error, error={}",bindingResult);
            return "mall/cart/cart";
        }

        List<CartForm> cartItems = cartService.findByIdIn(checkedCartItemIds); // checked

        List<CartForm> cartItemsForm = cartForm.getCartFormList(); //all(by form)
        for (CartForm form : cartItemsForm) {
            for (CartForm cartItem : cartItems) {
                if(cartItem.getCartItemId()==form.getCartItemId()){
                    cartItem.setQuantity(form.getQuantity());
                }
            }
        }

        int myCash = orderService.getCashByMemberId(memberId).intValue();
        int totalPrice = cartItems.stream().mapToInt(
                cartItem -> cartItem.getPrice() * cartItem.getQuantity()
        ).sum();

        if(myCash < totalPrice){
            bindingResult.reject("cash", new Object[]{myCash,totalPrice},null);
            log.info("need more cash");
            return "mall/cart/cart";
        }

        cartItems.stream().forEach(
                cartItem -> {
                    if(cartItem.getStock() < cartItem.getQuantity()){
                        bindingResult.reject("quantity",
                                "You entered more quantity than remaining stock");
                        log.info("entered more quantity than remaining stock");
                    }
                });
        cartService.saveCartItems(cartItems,memberId,totalPrice);
        return "redirect:/member/myProfile";
    }

    @PostMapping("/add")
    public String addItemToCart(
            @SessionAttribute(name = "memberId") Long memberId,
            @RequestParam(name = "itemId") Long itemId
    ){
        cartService.saveItemToCart(memberId, itemId);
        return "redirect:/mall/cart";
    }

    @ResponseBody
    @PostMapping("/delete")
    public String deleteCartItem(
            @RequestParam("cartItemId") Long cartItemId
    ){
        cartService.delete(cartItemId);
        return null;
    }

}
