package com.coffehouse.app.controller;

import com.coffehouse.app.model.MenuPosition;
import com.coffehouse.app.model.Order;
import com.coffehouse.app.model.dto.MenuPositionListDTO;
import com.coffehouse.app.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @GetMapping("/menu")
    public ResponseEntity<List<MenuPosition>> getMenu(){;
        return ResponseEntity.ok(userService.getMenu());
    }

    @PostMapping("/order")
    public ResponseEntity<String> createOrder(@RequestBody MenuPositionListDTO orderPositions){
        String resp = userService.createOrder(orderPositions);
        if(resp.isEmpty()){
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body("Order created");
        }else{
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resp);
        }
    }

    @GetMapping("/order/active")
    public ResponseEntity<List<Order>> getActiveOrders(){;
        return ResponseEntity.ok(userService.getActiveOrders());
    }

    @GetMapping("/order/inactive")
    public ResponseEntity<List<Order>> getInactiveOrders(){;
        return ResponseEntity.ok(userService.getInactiveOrders());
    }

    @GetMapping("/order/all")
    public ResponseEntity<List<Order>> getOrders(){;
        return ResponseEntity.ok(userService.getOrders());
    }
}
