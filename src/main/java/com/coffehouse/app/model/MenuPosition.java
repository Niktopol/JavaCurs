package com.coffehouse.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "menu_positions")
public class MenuPosition {
    @AllArgsConstructor
    @Getter
    public enum MenuPositions{
        CACAO(1L,"Какао", 90),
        MATCHA(2L,"Матча", 110),
        TEA(3L,"Чай", 80),
        AMERICANO(4L,"Американо", 80),
        CAPPUCCINO(5L,"Капучино", 100),
        LATTE(6L,"Латте", 100),
        MOCHACCINO(7L,"Мокачино", 100),
        RAF(8L,"Раф", 90),
        ESPRESSO(9L,"Эспрессо", 80);
        private final Long id;
        private final String name;
        private final float price;
    }
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private float price;

    @OneToMany(mappedBy = "menuPosition")
    @JsonIgnore
    private Set<OrderPosition> orderPositions;

    public MenuPosition(Long id, String name, float price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
