package ru.pankov.service.inter;

import ru.pankov.entity.Customer;
import ru.pankov.entity.Product;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerService {

    void addProduct(long customerId, long productId, int count);

    void removeProduct(long customerId, long productId);

    List<Product> getProductsInBasket(long customerId);

    void clearBasket(long customerId);

    void makeOrder(long customerId);

    Customer createCustomer(String name, String email);

    @Transactional
    List<Customer> getCustomers();
}
