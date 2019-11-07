package com.wg.base.show.repository;

import com.wg.base.show.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CustomerRepository extends JpaRepository<Customer,Long>,
        QuerydslPredicateExecutor<Customer> {

    Customer getById(Long id);

    Customer getByCustomerName(String customerName);
}
