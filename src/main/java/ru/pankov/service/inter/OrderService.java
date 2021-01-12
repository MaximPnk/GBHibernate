package ru.pankov.service.inter;

import ru.pankov.entity.Order;
import ru.pankov.entity.Product;

public interface OrderService {

    void saveOrUpdate(Order order);

}
