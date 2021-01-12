package ru.pankov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.pankov.dao.inter.OrderDAO;
import ru.pankov.entity.Order;

import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    SessionFactory sessionFactory;

    @Autowired
    public OrderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Order findById(long id) {
        Session session = getSession();
        return session.get(Order.class, id);
    }

    @Override
    public List<Order> findAll() {
        Session session = getSession();
        return session.createQuery("from Order", Order.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Session session = getSession();
        Order order = session.get(Order.class, id);
        session.delete(order);
    }

    @Override
    public Order saveOrUpdate(Order order) {
        Session session = getSession();
        session.saveOrUpdate(order);
        return order;
    }
}
