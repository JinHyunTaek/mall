package com.ht.mall.repository.item;

import com.ht.mall.entity.Item;
import com.ht.mall.entity.ItemCategory;
import com.ht.mall.entity.ItemImage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage,Long> {
    List<ItemImage> findByItemId(Long itemId);
}
