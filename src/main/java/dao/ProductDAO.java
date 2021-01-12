package dao;

import entity.Product;

import java.util.List;

public interface ProductDAO {

    Product findById(long id);

    List<Product> findAll();

    void deleteById(long id);

    Product saveOrUpdate(Product product);
}
