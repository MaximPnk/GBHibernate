package ru.pankov.dao.inter;

import ru.pankov.entity.Customer;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerDAO {
    @Transactional
    Customer findById(long id);

    @Transactional
    List<Customer> findAll();

    @Transactional
    void deleteById(long id);

    @Transactional
    Customer saveOrUpdate(Customer customer);
}
