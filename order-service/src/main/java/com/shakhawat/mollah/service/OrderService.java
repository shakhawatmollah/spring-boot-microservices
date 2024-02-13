package com.shakhawat.mollah.service;

import com.shakhawat.mollah.dto.OrderItemDto;
import com.shakhawat.mollah.dto.OrderRequest;
import com.shakhawat.mollah.model.Order;
import com.shakhawat.mollah.model.OrderItem;
import com.shakhawat.mollah.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItem> orderLineItems = orderRequest.getOrderItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderItemList(orderLineItems);

        orderRepository.save(order);
    }

    private OrderItem mapToDto(OrderItemDto orderLineItemsDto) {
        OrderItem orderLineItems = new OrderItem();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
