package ru.pankov.dao.inter;

import ru.pankov.entity.Order;
import ru.pankov.entity.OrderItem;
import ru.pankov.entity.Product;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderItemDAO {
    @Transactional
    OrderItem findById(long id);

    @Transactional
    List<OrderItem> findAll();

    @Transactional
    void deleteById(long id);

    @Transactional
    OrderItem saveOrUpdate(OrderItem orderItem);

    OrderItem findByOrderAndProduct(Order order, Product product);

    List<OrderItem> findByOrder(Order order);
}
