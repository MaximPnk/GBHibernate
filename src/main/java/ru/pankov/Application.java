package ru.pankov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.pankov.config.SpringConfig;
import ru.pankov.entity.Customer;
import ru.pankov.service.inter.CustomerService;
import ru.pankov.service.inter.ProductService;

public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        ProductService productService = context.getBean(ProductService.class);

        /*
         * Functions:
         *
         * CUSTOMER SERVICE:
         * addProduct(customerId, productId, count)
         * removeProduct(customerId, productId)
         * getProductsInBasket(customerId)
         * clearBasket(customerId)
         * makeOrder(customerId)
         * createCustomer(name, email)
         * getCustomers()
         *
         * PRODUCT SERVICE:
         * getProducts()
         *
         */

        System.out.println(productService.getProducts());
        System.out.println(customerService.getCustomers());

        Customer customer = customerService.createCustomer("Whilley", "j.whilley@gmail.com");

        customerService.addProduct(customer.getId(), 1, 10);
        customerService.addProduct(customer.getId(), 2, 10);
        customerService.addProduct(customer.getId(), 3, 10);
        customerService.addProduct(customer.getId(), 5, 15);
        System.out.println(customerService.getProductsInBasket(customer.getId()));

        customerService.removeProduct(customer.getId(), 5);
        System.out.println(customerService.getProductsInBasket(customer.getId()));

        customerService.clearBasket(customer.getId());
        System.out.println(customerService.getProductsInBasket(customer.getId()));

        customerService.addProduct(customer.getId(), 1, 20);
        customerService.addProduct(customer.getId(), 2, 20);
        customerService.addProduct(customer.getId(), 3, 20);
        System.out.println(customerService.getProductsInBasket(customer.getId()));
        customerService.makeOrder(customer.getId());
        System.out.println(productService.getProducts());
    }

}
