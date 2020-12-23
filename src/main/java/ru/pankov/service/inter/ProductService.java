package ru.pankov.service.inter;

import ru.pankov.entity.Product;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductService {

    Product getProduct(long id);

    List<Product> getProducts();

    @Transactional
    void saveOrUpdate(Product product);
}
