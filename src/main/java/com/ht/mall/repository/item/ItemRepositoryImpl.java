package com.ht.mall.repository.item;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.ItemSimpleDto;
import com.ht.mall.dto.QItemSimpleDto;
import com.ht.mall.entity.ItemCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ht.mall.entity.QItem.item;
import static com.ht.mall.entity.QItemImage.itemImage;

public class ItemRepositoryImpl implements ItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ItemSimpleDto> simpleItem(Pageable pageable, PageItemCond itemCond) {
        List<ItemSimpleDto> items = queryFactory
                .select(
                        new QItemSimpleDto(
                                item.id, item.itemName,
                                item.representImage.storedImageName,
                                item.price, item.createdDate
                        ))
                .from(item)
                .where(
                        itemCategoryEq(itemCond.getItemCategory())
                )
                .join(item.representImage, itemImage)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(item.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(item.countDistinct())
                .from(item)
                .where(
                        itemCategoryEq(itemCond.getItemCategory())
                );
        return PageableExecutionUtils.getPage(items,pageable,() -> countQuery.fetchOne());
    }

    private BooleanExpression itemCategoryEq(ItemCategory itemCategory) {
        return itemCategory != null ? item.itemCategory.eq(itemCategory) : null;
    }


}
