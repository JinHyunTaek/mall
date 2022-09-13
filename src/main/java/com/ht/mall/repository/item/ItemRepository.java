package com.ht.mall.repository.item;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.ItemCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long>, ItemRepositoryCustom {
    List<Item> findTop10ByItemCategoryEqualsOrderByIdDesc(ItemCategory itemCategory);

    @EntityGraph(attributePaths = {"member"})
    Item findWithMemberById(Long itemId);
}
