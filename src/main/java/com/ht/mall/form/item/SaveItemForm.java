package com.ht.mall.form.item;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.enumType.ItemCategory;
import com.ht.mall.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

@Getter @Setter
public class SaveItemForm {

    @NotEmpty
    @Size(min = 2,max = 10)
    private String itemName;

    private List<MultipartFile> imageFiles;

    @Min(value = 1000)
    @Max(value = 1000000)
    private Integer price;

    @Min(value = 1)
    @Max(value = 999)
    private Integer stock;

    @NotEmpty
    @Size(min = 5,max = 100)
    private String description;

    private Member member;

    @NotNull
    private ItemCategory itemCategory;

    private List<ItemCategory> itemCategories;

    public Item toEntity(){
        return Item.builder()
                .itemName(itemName)
                .price(price)
                .stock(stock)
                .description(description)
                .member(member)
                .itemCategory(itemCategory)
                .build();
    }

}
