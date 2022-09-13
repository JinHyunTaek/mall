package com.ht.mall.form;

import com.ht.mall.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import static com.ht.mall.entity.OrderStatus.VALID;

@Getter @Setter
@Builder
public class OrderForm {

    //item
    private Long itemId;

    private String itemName;

    private String representItemName;

    private Integer stock;

    private Integer price;

    //member
    private String city;

    private String zipcode;

    //input
    @Min(value = 1)
    @Max(value = 100)
    private Integer quantity;

    public OrderForm toForm(Item item, Member member){
        return OrderForm.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .representItemName(item.getRepresentImage().getStoredImageName())
                .stock(item.getStock())
                .price(item.getPrice())
                .city(member.getAddress().getCity())
                .zipcode(member.getAddress().getZipcode())
                .build();
    }

    public Order toEntity(Member member){
        return Order.builder()
                .orderStatus(VALID)
                .member(member)
                .build();
    }

    public OrderItem toEntity(OrderForm orderForm,Order order, Item item){
        return OrderItem.builder()
                .order(order)
                .item(item)
                .orderPrice(orderForm.getPrice()*orderForm.getQuantity())
                .quantity(orderForm.getQuantity())
                .build();
    }


}
