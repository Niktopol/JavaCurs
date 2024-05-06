package com.coffehouse.app.repository;

import com.coffehouse.app.model.OrderPosition;
import com.coffehouse.app.model.key.PositionCountKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPositionRepository extends JpaRepository<OrderPosition, PositionCountKey> {
}
