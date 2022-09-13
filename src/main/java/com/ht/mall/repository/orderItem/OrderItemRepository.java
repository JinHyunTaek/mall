package com.ht.mall.repository.orderItem;

import com.ht.mall.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>,OrderItemRepositoryCustom{
}
