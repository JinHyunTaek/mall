package com.ht.mall.repository;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findTop10ByItemCategoryEqualsOrderByIdDesc(ItemCategory itemCategory);
}
