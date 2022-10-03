package com.ht.mall.form.cart;

import com.ht.mall.entity.*;
import com.ht.mall.entity.enumType.OrderStatus;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.List;

import static com.ht.mall.entity.enumType.DeliveryStatus.SHIPPING_YET;

@Getter
@Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartForm {

    private List<CartForm> cartFormList;

    private Long cartItemId;

    private String itemName;

    private String representImageName;

    private Integer price;

    private Integer stock;

    @Min(value = 1)
    @Max(value = 999)
    private Integer quantity;

    public static CartForm toForm(CartItem cartItem){
        return CartForm.builder()
                .cartItemId(cartItem.getId())
                .itemName(cartItem.getItem().getItemName())
                .representImageName(
                        cartItem.getItem().getRepresentImage().getStoredImageName()
                )
                .stock(cartItem.getItem().getStock())
                .price(cartItem.getItem().getPrice())
                .build();
    }

    public Order toOrder(Member member){
        return Order.builder()
                .member(member)
                .orderStatus(OrderStatus.CANCEL_ENABLED)
                .build();
    }

    public OrderItem toOrderItem(CartForm cartForm, Order order, Item item){
        return OrderItem.builder()
                .order(order)
                .item(item)
                .delivery(
                        Delivery.builder()
                                .deliveryStatus(SHIPPING_YET)
                                .build()
                )
                .orderPrice(cartForm.getPrice()*cartForm.getQuantity())
                .quantity(cartForm.getQuantity())
                .build();
    }


}
