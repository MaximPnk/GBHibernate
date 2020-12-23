package ru.pankov.dao.inter;

import ru.pankov.entity.Product;

import java.util.List;

public interface ProductDAO {

    Product findById(long id);

    List<Product> findAll();

    void deleteById(long id);

    Product saveOrUpdate(Product product);
}
