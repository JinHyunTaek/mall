package com.ht.mall.repository.orderItem;

import com.ht.mall.condition.PageItemCond;
import com.ht.mall.dto.OrderItemSimpleDto;
import com.ht.mall.dto.QOrderItemSimpleDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.ht.mall.entity.QDelivery.delivery;
import static com.ht.mall.entity.QItem.item;
import static com.ht.mall.entity.QItemImage.itemImage;
import static com.ht.mall.entity.QMember.member;
import static com.ht.mall.entity.QOrder.order;
import static com.ht.mall.entity.QOrderItem.orderItem;

public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public OrderItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderItemSimpleDto> simpleOrderItem(Pageable pageable, PageItemCond itemCond) {
        List<OrderItemSimpleDto> orderItems = queryFactory.select(
                        new QOrderItemSimpleDto(
                                item.id, item.itemName, item.representImage.storedImageName,
                                item.price, orderItem.id, orderItem.quantity,
                                orderItem.orderPrice, orderItem.createdDate, orderItem.delivery.deliveryStatus
                        ))
                .from(orderItem)
                .join(orderItem.item, item)
                .join(item.representImage, itemImage)
                .join(orderItem.delivery, delivery)
                .join(orderItem.order,order)
                .join(order.member, member)
                .where(
                        memberIdEq(itemCond.getMemberId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderItem.id.desc())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(orderItem.countDistinct())
                .from(orderItem)
                .where(
                        memberIdEq(itemCond.getMemberId())
                );
        return PageableExecutionUtils.getPage(orderItems,pageable,() -> countQuery.fetchOne());

    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? order.member.id.eq(memberId) : null;
    }
}
