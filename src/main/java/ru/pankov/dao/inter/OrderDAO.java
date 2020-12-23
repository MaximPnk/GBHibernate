package ru.pankov.dao.inter;

import ru.pankov.entity.Order;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderDAO {
    @Transactional
    Order findById(long id);

    @Transactional
    List<Order> findAll();

    @Transactional
    void deleteById(long id);

    @Transactional
    Order saveOrUpdate(Order order);
}
