package com.ht.mall.condition;

import com.ht.mall.entity.enumType.ItemCategory;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageItemCond {

    private ItemCategory itemCategory;

    private Long memberId;

}
