package com.shakhawat.mollah.repository;

import com.shakhawat.mollah.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
