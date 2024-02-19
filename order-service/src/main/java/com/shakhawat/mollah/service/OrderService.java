package com.shakhawat.mollah.service;

import com.shakhawat.mollah.dto.InventoryResponse;
import com.shakhawat.mollah.dto.OrderItemDto;
import com.shakhawat.mollah.dto.OrderRequest;
import com.shakhawat.mollah.model.Order;
import com.shakhawat.mollah.model.OrderItem;
import com.shakhawat.mollah.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItem> orderLineItems = orderRequest.getOrderItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderItemList(orderLineItems);

        List<String> skuCodes = order.getOrderItemList().stream()
                .map(OrderItem::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                        .retrieve()
                                .bodyToMono(InventoryResponse[].class)
                                        .block();

        boolean result = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);

        if(Boolean.TRUE.equals(result)){
            orderRepository.save(order);
            return "Order Placed Successfully";
        }else{
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }


    }

    private OrderItem mapToDto(OrderItemDto orderLineItemsDto) {
        OrderItem orderLineItems = new OrderItem();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
