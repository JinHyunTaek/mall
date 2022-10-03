package com.ht.mall.repository.orderItem;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.ItemSimpleDto;
import com.ht.mall.dto.OrderItemSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderItemRepositoryCustom {
    Page<OrderItemSimpleDto> simpleOrderItem(Pageable pageable, PageItemCond itemCond);
}
