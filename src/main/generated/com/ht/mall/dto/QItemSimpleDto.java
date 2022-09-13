package com.ht.mall.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ht.mall.dto.QItemSimpleDto is a Querydsl Projection type for ItemSimpleDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QItemSimpleDto extends ConstructorExpression<ItemSimpleDto> {

    private static final long serialVersionUID = -1271379290L;

    public QItemSimpleDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> itemName, com.querydsl.core.types.Expression<String> representImageName, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<java.time.LocalDateTime> createdAt) {
        super(ItemSimpleDto.class, new Class<?>[]{long.class, String.class, String.class, int.class, java.time.LocalDateTime.class}, id, itemName, representImageName, price, createdAt);
    }

}

