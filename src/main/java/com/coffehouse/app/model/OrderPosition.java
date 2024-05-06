package com.coffehouse.app.model;

import com.coffehouse.app.model.key.PositionCountKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_positions")
public class OrderPosition {
    @EmbeddedId
    PositionCountKey id;

    @ManyToOne
    @MapsId("orderId")
    @JsonIgnoreProperties("orderPositions")
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @MapsId("menuPosId")
    @JsonIgnoreProperties({"id", "orderPositions"})
    @JoinColumn(name = "menuposition_id")
    MenuPosition menuPosition;

    int count;
}
