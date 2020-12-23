package ru.pankov.service.inter;

import ru.pankov.entity.Order;
import ru.pankov.entity.OrderItem;
import ru.pankov.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemService {

    void addOrderItem(Order order, Product product, int count, BigDecimal price);

    void remove(Order order, Product product);

    List<OrderItem> getByOrder(Order order);

    void deleteByOrder(Order order);
}
