package com.ht.mall.service.mall;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.ItemCategory;
import com.ht.mall.repository.ItemImageRepository;
import com.ht.mall.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MainService {

    private final ItemRepository itemRepository;

    public void setMallMain(Model model){
        List<Item> items = itemRepository.findTop10ByItemCategoryEqualsOrderByIdDesc(ItemCategory.CLOTHES);
        model.addAttribute("items",items);
    }

}
