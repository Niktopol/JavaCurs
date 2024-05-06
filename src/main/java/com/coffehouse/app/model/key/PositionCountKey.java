package com.coffehouse.app.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class PositionCountKey implements Serializable {
    @Column(name = "order_id")
    Long orderId;

    @Column(name = "menuposition_id")
    Long menuPosId;

    @Override
    public int hashCode() {
        return (String.valueOf(orderId) + String.valueOf(menuPosId)).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(((PositionCountKey) obj).getOrderId(), this.orderId) &&
                Objects.equals(((PositionCountKey) obj).getMenuPosId(), this.menuPosId);
    }
}
