package com.ht.mall.repository.cart;

import com.ht.mall.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByMemberId(Long memberId);
}
