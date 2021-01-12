package ru.pankov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankov.dao.inter.OrderDAO;
import ru.pankov.entity.Order;
import ru.pankov.service.inter.OrderService;

import javax.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    @Transactional
    public void saveOrUpdate(Order order) {
        orderDAO.saveOrUpdate(order);
    }
}
