package ru.pankov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankov.dao.inter.CustomerDAO;
import ru.pankov.entity.Customer;
import ru.pankov.entity.Order;
import ru.pankov.entity.OrderItem;
import ru.pankov.entity.Product;
import ru.pankov.service.inter.CustomerService;
import ru.pankov.service.inter.OrderItemService;
import ru.pankov.service.inter.OrderService;
import ru.pankov.service.inter.ProductService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerDAO customerDAO;
    OrderService orderService;
    ProductService productService;
    OrderItemService orderItemService;

    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, OrderService orderService, ProductService productService, OrderItemService orderItemService) {
        this.customerDAO = customerDAO;
        this.orderService = orderService;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    @Override
    @Transactional
    public void addProduct(long customerId, long productId, int count) {
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id = " + customerId + " doesn't exists");
        }

        Product product = productService.getProduct(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with id = " + productId + " doesn't exists");
        } else if (product.getCount() < count) {
            throw new IllegalArgumentException("Insufficient amount of items");
        }

        Order order;
        if (customer.getOrders().size() == 0 || customer.getOrders().stream().allMatch(Order::isPayed)) {
            order = new Order(customer);
            orderService.saveOrUpdate(order);
        } else if (customer.getOrders().stream().filter(o -> !o.isPayed()).count() == 1) {
            order = customer.getOrders().stream().filter(o -> !o.isPayed()).findFirst().get();
        } else {
            throw new UnsupportedOperationException("Some problems on the server. Contact the support.");
        }

        orderItemService.addOrderItem(order, product, count, product.getPrice());
    }

    @Override
    @Transactional
    public void removeProduct(long customerId, long productId) {
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id = " + customerId + " doesn't exists");
        }

        Product product = productService.getProduct(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product with id = " + productId + " doesn't exists");
        }

        Order order;

        if (customer.getOrders().size() == 0 || customer.getOrders().stream().allMatch(Order::isPayed)) {
            throw new IllegalStateException("Your basket is free");
        } else if (customer.getOrders().stream().filter(o -> !o.isPayed()).count() == 1) {
            order = customer.getOrders().stream().filter(o -> !o.isPayed()).findFirst().get();
        } else {
            throw new UnsupportedOperationException("Some problems on the server. Contact the support.");
        }

        if (orderItemService.getByOrder(order).size() == 0) {
            throw new IllegalStateException("Your basket is free");
        }

        orderItemService.remove(order, product);
    }

    @Override
    @Transactional
    public List<Product> getProductsInBasket(long customerId) {
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id = " + customerId + " doesn't exists");
        }

        Order order;

        if (customer.getOrders().size() == 0 || customer.getOrders().stream().allMatch(Order::isPayed)) {
            return Collections.emptyList();
        } else if (customer.getOrders().stream().filter(o -> !o.isPayed()).count() == 1) {
            order = customer.getOrders().stream().filter(o -> !o.isPayed()).findFirst().get();
        } else {
            throw new UnsupportedOperationException("Some problems on the server. Contact the support.");
        }

        List<OrderItem> orderItems = orderItemService.getByOrder(order);

        if (orderItems.size() == 0) {
            return Collections.emptyList();
        } else {
            List<Product> products = new ArrayList<>();
            for (OrderItem oi : orderItems) {
                products.add(new Product(oi.getProduct().getId(), oi.getProduct().getName(), oi.getPrice(), oi.getCount()));
            }
            return products;
        }
    }

    @Override
    @Transactional
    public void clearBasket(long customerId) {
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id = " + customerId + " doesn't exists");
        }

        Order order;

        if (customer.getOrders().size() == 0 || customer.getOrders().stream().allMatch(Order::isPayed)) {
            return;
        } else if (customer.getOrders().stream().filter(o -> !o.isPayed()).count() == 1) {
            order = customer.getOrders().stream().filter(o -> !o.isPayed()).findFirst().get();
        } else {
            throw new UnsupportedOperationException("Some problems on the server. Contact the support.");
        }

        orderItemService.deleteByOrder(order);
    }

    @Override
    @Transactional
    public void makeOrder(long customerId) {
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with id = " + customerId + " doesn't exists");
        }

        Order order;

        if (customer.getOrders().size() == 0 || customer.getOrders().stream().allMatch(Order::isPayed)) {
            throw new IllegalStateException("Your basket is free");
        } else if (customer.getOrders().stream().filter(o -> !o.isPayed()).count() == 1) {
            order = customer.getOrders().stream().filter(o -> !o.isPayed()).findFirst().get();
        } else {
            throw new UnsupportedOperationException("Some problems on the server. Contact the support.");
        }

        if (orderItemService.getByOrder(order).size() == 0) {
            throw new IllegalStateException("Your basket is free");
        } else {
            List<OrderItem> orderItems = orderItemService.getByOrder(order);
            for (OrderItem oi : orderItems) {
                Product product = productService.getProduct(oi.getProduct().getId());
                if (product.getCount() >= oi.getCount()) {
                    product.setCount(product.getCount() - oi.getCount());
                    productService.saveOrUpdate(product);
                } else {
                    throw new RuntimeException("Insufficient amount of items " + product.getName() + ". " +
                            "Available count is " + product.getCount());
                }
            }
        }

        System.out.println(order);
        orderItemService.deleteByOrder(order);
        order.setPayed(true);
        orderService.saveOrUpdate(order);
    }

    @Override
    @Transactional
    public Customer createCustomer(String name, String email) {
        return customerDAO.saveOrUpdate(new Customer(name, email));
    }

    @Override
    @Transactional
    public List<Customer> getCustomers() {
        return customerDAO.findAll();
    }
}
