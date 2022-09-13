package com.ht.mall.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemSimpleDto {

    private Long id;

    private String itemName;

    private String representImageName;

    private Integer price;

    private LocalDateTime createdAt;

    @QueryProjection
    public ItemSimpleDto(Long id, String itemName, String representImageName, Integer price, LocalDateTime createdAt) {
        this.id = id;
        this.itemName = itemName;
        this.representImageName = representImageName;
        this.price = price;
        this.createdAt = createdAt;
    }
}
