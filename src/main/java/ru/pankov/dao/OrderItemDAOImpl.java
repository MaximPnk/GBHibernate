package ru.pankov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.pankov.dao.inter.OrderItemDAO;
import ru.pankov.entity.Order;
import ru.pankov.entity.OrderItem;
import ru.pankov.entity.Product;

import java.util.List;

@Repository
public class OrderItemDAOImpl implements OrderItemDAO {

    SessionFactory sessionFactory;

    @Autowired
    public OrderItemDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public OrderItem findById(long id) {
        Session session = getSession();
        return session.get(OrderItem.class, id);
    }

    @Override
    public List<OrderItem> findAll() {
        Session session = getSession();
        return session.createQuery("from OrderItem", OrderItem.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Session session = getSession();
        OrderItem orderItem = session.get(OrderItem.class, id);
        session.delete(orderItem);
    }

    @Override
    public OrderItem saveOrUpdate(OrderItem orderItem) {
        Session session = getSession();
        session.saveOrUpdate(orderItem);
        return orderItem;
    }

    @Override
    public OrderItem findByOrderAndProduct(Order order, Product product) {
        Session session = getSession();
        Query<OrderItem> query = session.createQuery("from OrderItem where order=:ord and product=:prod", OrderItem.class);
        query.setParameter("ord", order);
        query.setParameter("prod", product);
        return query.uniqueResult();
    }

    @Override
    public List<OrderItem> findByOrder(Order order) {
        Session session = getSession();
        Query<OrderItem> query = session.createQuery("from OrderItem where order=:ord ", OrderItem.class);
        query.setParameter("ord", order);
        return query.getResultList();
    }
}
