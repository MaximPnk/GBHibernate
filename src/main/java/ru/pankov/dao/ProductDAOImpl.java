package ru.pankov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.pankov.dao.inter.ProductDAO;
import ru.pankov.entity.Product;

import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    SessionFactory sessionFactory;

    @Autowired
    public ProductDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Product findById(long id) {
        Session session = getSession();
        return session.get(Product.class, id);
    }

    @Override
    public List<Product> findAll() {
        Session session = getSession();
        return session.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Session session = getSession();
        Product product = session.get(Product.class, id);
        session.delete(product);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        Session session = getSession();
        session.saveOrUpdate(product);
        return product;
    }
}
