package com.coffehouse.app.controller;

import com.coffehouse.app.model.Order;
import com.coffehouse.app.service.WorkerService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/worker")
public class WorkerController {

    WorkerService workerService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders(){
        return ResponseEntity.ok(workerService.getOrders());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id){
        Order order = workerService.getOrder(id);
        if (order != null){
            return ResponseEntity.ok(order);
        }else{
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(null);
        }
    }

    @PostMapping("/order/close/{id}")
    public ResponseEntity<String> closeOrder(@PathVariable Long id){
        String resp = workerService.closeOrder(id);
        if (resp.isEmpty()){
            return ResponseEntity.ok("Order with id " + id + " was closed");
        }else{
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resp);
        }
    }
}
