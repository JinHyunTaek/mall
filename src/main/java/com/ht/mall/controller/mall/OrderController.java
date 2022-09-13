package com.ht.mall.controller.mall;

import com.ht.mall.form.OrderForm;
import com.ht.mall.repository.order.OrderRepository;
import com.ht.mall.repository.item.ItemRepository;
import com.ht.mall.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mall/order")
@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderService orderService;

    @GetMapping("/buy")
    public String order(OrderForm orderForm,
                        @RequestParam("itemId") Long itemId,
                        @SessionAttribute("memberId") Long memberId,
                        Model model){
        OrderForm form = orderService.setItemOrder(itemId, memberId, orderForm);
        model.addAttribute("order",form);
        return "mall/order/addForm";
    }

    @PostMapping("/buy")
    public String order(@Validated @ModelAttribute("order") OrderForm orderForm,
                        BindingResult bindingResult,
                        @SessionAttribute("memberId") Long memberId){
        Integer myCash = orderService.getCashByMemberId(memberId);
        Integer totalPrice = orderForm.getPrice()*orderForm.getQuantity();

        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            return "mall/order/addForm";
        }
        if(myCash<totalPrice){
            bindingResult.reject("cash", new Object[]{myCash,totalPrice},null);
            log.info("need more cash");
            return "mall/order/addForm";
        }

        if(orderForm.getQuantity()>orderForm.getStock()){
            bindingResult.reject("quantity",
                    "You entered more quantity than remaining stock");
            log.info("entered more quantity than remaining stock");
        }
        orderService.saveOrderItem(orderForm,memberId);
        return "redirect:/member/myProfile?memberId="+memberId;
    }

}
