package dao;

import entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Product.class)
                .buildSessionFactory();
    }

    @Override
    public Product findById(long id) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.getTransaction().commit();

        return product;
    }

    @Override
    public List<Product> findAll() {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        List<Product> products = session.createQuery("from Product", Product.class).getResultList();
        session.getTransaction().commit();

        return products;
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.delete(product);
        session.getTransaction().commit();
    }

    @Override
    public Product saveOrUpdate(Product product) {
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.saveOrUpdate(product);
        session.getTransaction().commit();

        return product;
    }
}
