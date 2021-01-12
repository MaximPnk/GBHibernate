package ru.pankov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankov.dao.inter.OrderItemDAO;
import ru.pankov.entity.Order;
import ru.pankov.entity.OrderItem;
import ru.pankov.entity.Product;
import ru.pankov.service.inter.OrderItemService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    OrderItemDAO orderItemDAO;

    @Autowired
    public OrderItemServiceImpl(OrderItemDAO orderItemDAO) {
        this.orderItemDAO = orderItemDAO;
    }

    @Override
    @Transactional
    public void addOrderItem(Order order, Product product, int count, BigDecimal price) {
        orderItemDAO.saveOrUpdate(new OrderItem(count, price, order, product));
    }

    @Override
    @Transactional
    public void remove(Order order, Product product) {
        OrderItem orderItem = orderItemDAO.findByOrderAndProduct(order, product);
        orderItemDAO.deleteById(orderItem.getId());
    }

    @Override
    @Transactional
    public List<OrderItem> getByOrder(Order order) {
        return orderItemDAO.findByOrder(order);
    }

    @Override
    @Transactional
    public void deleteByOrder(Order order) {
        orderItemDAO.findByOrder(order).forEach(o -> orderItemDAO.deleteById(o.getId()));
    }
}
