package com.example.demo.Service;

import com.example.demo.Entity.OrderEntity;
import com.example.demo.Entry.OrderEntry;
import com.example.demo.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DeliveryExecutiveService deliveryExecutiveService;

    public List<OrderEntry> findOrdersForExecutive(Long deliveryExecutiveId) {
        List<OrderEntity> orderEntities = repository.findOrdersForExecutive(deliveryExecutiveId);
        List<OrderEntry> entries = orderEntities.stream().map(orderEntity -> convertToEntry(orderEntity)).toList();
        return entries;
    }

    public OrderEntry convertToEntry(OrderEntity orderEntity) {
        OrderEntry entry = new OrderEntry();
        entry.setOrderId(orderEntity.getOrderId());
        entry.setConsumerEntry(consumerService.findConsumer(orderEntity.getConsumerId()));
        entry.setRestaurantEntry(restaurantService.findRestaurant(orderEntity.getRestaurantId()));
        entry.setDeliveryExecutiveEntry(deliveryExecutiveService.findExecutive(orderEntity.getDeliveryExecutiveId()));
        return entry;
    }
}