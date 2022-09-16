package com.ht.mall.dto;

import com.ht.mall.entity.enumType.ItemCategory;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ItemDetailDto {

    private Long id;

    private String itemName;

    private List<String> storedImageNames;

    private ItemCategory itemCategory;

    private String memberName;

    private String description;

    private Integer price;

    private Integer stock;

    private LocalDateTime createdAt;

    public ItemDetailDto(Long id, String itemName, List<String> storedImageNames,
                         ItemCategory itemCategory, String memberName,
                         String description, Integer price, Integer stock, LocalDateTime createdAt) {
        this.id = id;
        this.itemName = itemName;
        this.storedImageNames = storedImageNames;
        this.itemCategory = itemCategory;
        this.memberName = memberName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.createdAt = createdAt;
    }
}
