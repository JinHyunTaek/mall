package com.ht.mall.form.item;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.enumType.ItemCategory;
import com.ht.mall.entity.ItemImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@Builder
public class ItemDetailForm {

    private Long itemId;

    private String itemName;

    private List<String> storedImageNames;

    private ItemCategory itemCategory;

    private Long memberId;

    private String memberName;

    private String description;

    private Integer price;

    private Integer stock;

    private Long likeCount;

    private LocalDateTime createdAt;

    public ItemDetailForm toForm(Item item, List<ItemImage> itemImages,Long likeCount){
        return ItemDetailForm.builder()
                .itemId(item.getId())
                .itemName(item.getItemName())
                .storedImageNames(itemImages.stream().map(
                        itemImage -> itemImage.getStoredImageName()
                ).collect(Collectors.toList()))
                .itemCategory(item.getItemCategory())
                .memberName(item.getMember().getName())
                .memberId(item.getMember().getId())
                .description(item.getDescription())
                .price(item.getPrice())
                .stock(item.getStock())
                .likeCount(likeCount)
                .createdAt(item.getCreatedDate())
                .build();
    }

}
