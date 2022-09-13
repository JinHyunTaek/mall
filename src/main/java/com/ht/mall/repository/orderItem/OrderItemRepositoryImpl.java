package com.ht.mall.repository.orderItem;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.ItemSimpleDto;
import com.ht.mall.dto.QItemSimpleDto;
import com.ht.mall.entity.QItem;
import com.ht.mall.entity.QItemImage;
import com.ht.mall.entity.QOrder;
import com.ht.mall.entity.QOrderItem;
import com.querydsl.core.types.Predicate;
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
import static com.ht.mall.entity.QOrder.order;
import static com.ht.mall.entity.QOrderItem.orderItem;

public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public OrderItemRepositoryImpl(EntityManager em) {
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
                .from(orderItem)
                .join(orderItem.item, item)
                .join(orderItem.order, order)
                .join(item.representImage, itemImage)
                .where(
                        order_memberIdEq(itemCond.getMemberId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(item.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(orderItem.countDistinct())
                .from(orderItem)
                .join(orderItem.order, order)
                .where(
                        order_memberIdEq(itemCond.getMemberId())
                );
        return PageableExecutionUtils.getPage(items,pageable,() -> countQuery.fetchOne());

    }
    private BooleanExpression order_memberIdEq(Long memberId) {
        return memberId != null ? order.member.id.eq(memberId) : null;
    }
}
