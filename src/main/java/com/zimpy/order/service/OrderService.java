package com.zimpy.order.service;

import com.zimpy.cart.entity.Cart;
import com.zimpy.cart.entity.CartItem;
import com.zimpy.cart.entity.CartStatus;
import com.zimpy.cart.repositories.CartItemRepository;
import com.zimpy.cart.repositories.CartRepository;
import com.zimpy.exception.BadRequestException;
import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.inventory.Service.InventoryService;
import com.zimpy.order.dto.OrderDetailResponse;
import com.zimpy.order.dto.OrderItemResponse;
import com.zimpy.order.dto.OrderListItemResponse;
import com.zimpy.order.dto.OrderResponse;
import com.zimpy.order.entity.Order;
import com.zimpy.order.entity.OrderItem;
import com.zimpy.order.entity.OrderStatus;
import com.zimpy.order.repository.OrderItemRepository;
import com.zimpy.order.repository.OrderRepository;

import com.zimpy.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository ;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InventoryService inventoryService;

    public OrderService(
            OrderRepository orderRepository,
            InventoryService inventoryService,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository){
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public OrderResponse checkout(Long userId) {

        Cart cart = cartRepository
                .findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .orElseThrow(() ->
                        new BadRequestException("Cart is empty")
                );

        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        //  Calculate total amount FIRST
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            BigDecimal price = item.getSku().getPrice();
            BigDecimal itemTotal =
                    price.multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        // Create order WITH totalAmount set
        Order order = new Order();
        order.setUser(userRepository.getReferenceById(userId));
        order.setStatus(OrderStatus.PAYMENT_PENDING);
        order.setTotalAmount(totalAmount);

        order = orderRepository.save(order); // ✅ SAFE INSERT

        // Create order items
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setSku(item.getSku());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getSku().getPrice());

            orderItemRepository.save(orderItem);
        }

        // Lock cart
        cart.setStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        return new OrderResponse(order.getId(), totalAmount);
    }


    public Page<OrderListItemResponse> getMyOrders(Long userId, Pageable pageable) {

        Page<Order> page = orderRepository
                .findByUserIdOrderByCreatedAtDesc(userId, pageable);

        return page.map(o ->
                new OrderListItemResponse(
                        o.getId(),
                        o.getStatus(),
                        o.getTotalAmount(),
                        o.getCreatedAt()
                )
        );
    }

    public OrderDetailResponse getMyOrder(Long userId, Long orderId) {

        Order order = orderRepository
                .findByIdAndUserId(orderId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found")
                );

        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());

        List<OrderItemResponse> itemResponses = items.stream()
                .map(i -> new OrderItemResponse(
                        i.getSku().getId(),
                        i.getSku().getSkuCode(),
                        i.getPrice(),
                        i.getQuantity()
                ))
                .toList();

        return new OrderDetailResponse(
                order.getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                itemResponses
        );
    }

    @Transactional
    public void cancelMyOrder(Long userId, Long orderId) {

        Order order = orderRepository
                .findByIdAndUserId(orderId, userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found")
                );

        if (!(order.getStatus() == OrderStatus.CREATED ||
                order.getStatus() == OrderStatus.PAYMENT_PENDING)) {
            throw new BadRequestException("Order cannot be cancelled now");
        }

        // release inventory for each item
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getId());
        for (OrderItem item : items) {
            inventoryService.releaseStock(
                    item.getSku().getId(),
                    item.getQuantity()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

}
