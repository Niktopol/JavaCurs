package com.coffehouse.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean closed;

    @Column(nullable = false)
    private float price;

    @ManyToOne
    @JsonIgnoreProperties({"orders", "password", "role", "enabled", "authorities",
            "accountNonLocked", "credentialsNonExpired", "accountNonExpired"})
    private User customer;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties({"order", "id"})
    private Set<OrderPosition> orderPositions;

    public Order(boolean closed, float price, User customer){
        this.closed = closed;
        this.price = price;
        this.customer = customer;
    }
}
