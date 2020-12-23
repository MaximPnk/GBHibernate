package ru.pankov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pankov.dao.inter.ProductDAO;
import ru.pankov.entity.Product;
import ru.pankov.service.inter.ProductService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    ProductDAO productDAO;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    @Transactional
    public Product getProduct(long id) {
        return productDAO.findById(id);
    }

    @Override
    @Transactional
    public List<Product> getProducts() {
        return productDAO.findAll();
    }

    @Override
    @Transactional
    public void saveOrUpdate(Product product) {
        productDAO.saveOrUpdate(product);
    }
}
