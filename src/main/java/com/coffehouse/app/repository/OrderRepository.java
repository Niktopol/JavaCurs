package com.coffehouse.app.repository;

import com.coffehouse.app.model.Order;
import com.coffehouse.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomerOrderByClosed(User customer);
    List<Order> findAllByClosedFalseAndCustomer(User customer);
    List<Order> findAllByClosedTrueAndCustomer(User customer);
    List<Order> findAllByClosedFalse();
}
