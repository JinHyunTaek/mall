package com.ht.mall.condition;

import com.ht.mall.entity.enumType.ItemCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class PageItemCond {

    private ItemCategory itemCategory;

    private String searchValue;

    private Long memberId;

}
