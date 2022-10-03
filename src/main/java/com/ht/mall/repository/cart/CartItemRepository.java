package com.ht.mall.repository.cart;

import com.ht.mall.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query("select ci from CartItem ci " +
            "join fetch ci.item i join fetch i.representImage ri " +
            "join ci.cart " +
            "where ci.cart.id=:cartId")
    List<CartItem> findWithImageByCartId(@Param("cartId") Long cartId);

    @Query("select ci from CartItem ci " +
            "join fetch ci.item i join fetch i.member m " +
            "where ci.id=:cartItemId")
    Optional<CartItem> findWithSalesmanById(@Param("cartItemId") Long id);

    List<CartItem> findByIdIn(List<Long> checkedItemIds);

}
