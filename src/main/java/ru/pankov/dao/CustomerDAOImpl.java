package ru.pankov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.pankov.dao.inter.CustomerDAO;
import ru.pankov.entity.Customer;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    SessionFactory sessionFactory;

    @Autowired
    public CustomerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Customer findById(long id) {
        Session session = getSession();
        return session.get(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        Session session = getSession();
        return session.createQuery("from Customer", Customer.class).getResultList();
    }

    @Override
    public void deleteById(long id) {
        Session session = getSession();
        Customer customer = session.get(Customer.class, id);
        session.delete(customer);
    }

    @Override
    public Customer saveOrUpdate(Customer customer) {
        Session session = getSession();
        session.saveOrUpdate(customer);
        return customer;
    }
}
