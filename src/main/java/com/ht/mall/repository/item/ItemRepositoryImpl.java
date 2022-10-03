package com.ht.mall.repository.item;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.ItemSimpleDto;
import com.ht.mall.dto.QItemSimpleDto;
import com.ht.mall.entity.enumType.ItemCategory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

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
                        itemCategoryEq(itemCond.getItemCategory()),
                        searchValueContains(itemCond.getSearchValue())
                )
                .join(item.representImage,itemImage)
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

    private BooleanExpression searchValueContains(String searchValue) {
        return StringUtils.hasText(searchValue) ?
                item.itemName.contains(searchValue).or(item.description.contains(searchValue)) : null;
    }

    private BooleanExpression itemCategoryEq(ItemCategory itemCategory) {
        return itemCategory != null ? item.itemCategory.eq(itemCategory) : null;
    }


}
