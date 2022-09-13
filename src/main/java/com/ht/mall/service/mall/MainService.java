package com.ht.mall.service.mall;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.ItemSimpleDto;
import com.ht.mall.entity.ItemCategory;
import com.ht.mall.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MainService {

    private final ItemRepository itemRepository;

    public Page<ItemSimpleDto> setMallMain(Pageable pageable, PageItemCond itemCond){
        return itemRepository.simpleItem(pageable,itemCond);
    }

}
