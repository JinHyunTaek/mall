package com.ht.mall.repository.item;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.ItemSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<ItemSimpleDto> simpleItem(Pageable pageable, PageItemCond itemCond);
}
