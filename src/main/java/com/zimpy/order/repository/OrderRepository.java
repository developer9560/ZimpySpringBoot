package com.zimpy.order.repository;

import com.zimpy.order.entity.Order;
import com.zimpy.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Page<Order> findByStatusAndUserId(OrderStatus status, Long userId, Pageable pageable);
    Optional<Order> findByIdAndUserId(Long id, Long userId);
}
