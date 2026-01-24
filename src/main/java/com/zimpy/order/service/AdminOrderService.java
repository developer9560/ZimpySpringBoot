package com.zimpy.order.service;

import com.zimpy.exception.BadRequestException;
import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.inventory.Service.InventoryService;
import com.zimpy.order.dto.AdminOrderDetailResponse;
import com.zimpy.order.dto.AdminOrderListResponse;
import com.zimpy.order.dto.OrderItemResponse;
import com.zimpy.order.entity.Order;
import com.zimpy.order.entity.OrderItem;
import com.zimpy.order.entity.OrderStatus;
import com.zimpy.order.repository.OrderItemRepository;
import com.zimpy.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AdminOrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository itemRepo;
    private final InventoryService inventoryService;

    public AdminOrderService(
            OrderRepository orderRepo,
            OrderItemRepository itemRepo,
            InventoryService inventoryService
    ) {
        this.orderRepo = orderRepo;
        this.itemRepo = itemRepo;
        this.inventoryService = inventoryService;
    }

    //  List orders
    public Page<AdminOrderListResponse> getAllOrders(
            OrderStatus status,
            Long userId,
            Pageable pageable
    ) {
        Page<Order> page;
        if (status != null && userId != null) {
            page = orderRepo.findByStatusAndUserId(status, userId, pageable);
        } else if (status != null) {
            page = orderRepo.findByStatus(status, pageable);
        } else if (userId != null) {
            page = orderRepo.findByUserId(userId, pageable);
        } else {
            page = orderRepo.findAll(pageable);
        }

        return page.map(o ->
                new AdminOrderListResponse(
                        o.getId(),
                        o.getUser().getId(),
                        o.getStatus(),
                        o.getTotalAmount(),
                        o.getCreatedAt()
                )
        );
    }

    // 2️⃣ Order details
    public AdminOrderDetailResponse getOrder(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found")
                );

        List<OrderItemResponse> items =
                itemRepo.findByOrderId(orderId).stream()
                        .map(i -> new OrderItemResponse(
                                i.getSku().getId(),
                                i.getSku().getSkuCode(),
                                i.getPrice(),
                                i.getQuantity()
                        ))
                        .toList();

        return new AdminOrderDetailResponse(
                order.getId(),
                order.getUser().getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                items
        );
    }

    // 3️⃣ Update status
    @Transactional
    public void updateStatus(Long orderId, OrderStatus newStatus) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found")
                );

        validateTransition(order.getStatus(), newStatus);

        order.setStatus(newStatus);
        orderRepo.save(order);
    }

    // 4️⃣ Cancel order
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found")
                );

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new BadRequestException("Delivered order cannot be cancelled");
        }

        // release inventory
        List<OrderItem> items = itemRepo.findByOrderId(orderId);
        for (OrderItem item : items) {
            inventoryService.releaseStock(
                    item.getSku().getId(),
                    item.getQuantity()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepo.save(order);
    }

    private void validateTransition(OrderStatus current, OrderStatus next) {

        Map<OrderStatus, Set<OrderStatus>> allowed = Map.of(
                OrderStatus.CREATED, Set.of(OrderStatus.PAYMENT_PENDING),
                OrderStatus.PAYMENT_PENDING, Set.of(OrderStatus.PAID),
                OrderStatus.PAID, Set.of(OrderStatus.SHIPPED),
                OrderStatus.SHIPPED, Set.of(OrderStatus.DELIVERED)
        );

        if (!allowed.getOrDefault(current, Set.of()).contains(next)) {
            throw new BadRequestException(
                    "Invalid status transition: " + current + " → " + next
            );
        }
    }
}
