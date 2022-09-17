package com.ht.mall.form;

import com.ht.mall.entity.*;
import com.ht.mall.entity.enumType.DeliveryStatus;
import com.ht.mall.entity.enumType.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Getter @Setter
@Builder
public class OrderForm {

    //item
    private Long itemId;

    private String itemName;

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
                .stock(item.getStock())
                .price(item.getPrice())
                .city(member.getAddress().getCity())
                .zipcode(member.getAddress().getZipcode())
                .build();
    }

    public Order toEntity(Member member){
        return Order.builder()
                .orderStatus(OrderStatus.CANCEL_ENABLED)
                .member(member)
                .build();
    }

    public OrderItem toEntity(OrderForm orderForm,Order order, Item item){
        return OrderItem.builder()
                .order(order)
                .item(item)
                .delivery(
                        Delivery.builder()
                                .deliveryStatus(DeliveryStatus.SHIPPING_YET)
                                .build()
                )
                .orderPrice(orderForm.getPrice()*orderForm.getQuantity())
                .quantity(orderForm.getQuantity())
                .build();
    }


}
