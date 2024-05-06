package com.coffehouse.app.service;

import com.coffehouse.app.model.Order;
import com.coffehouse.app.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class WorkerService {
    OrderRepository orderRepository;

    public List<Order> getOrders(){
        return orderRepository.findAllByClosedFalse();
    }

    public Order getOrder(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    public String closeOrder(Long id){
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null){
            return "Order with id " + id +" does not exist";
        }else if(order.isClosed()){
            return "Order with id " + id +" was already closed";
        }else{
            order.setClosed(true);
            orderRepository.save(order);
            return "";
        }
    }
}
