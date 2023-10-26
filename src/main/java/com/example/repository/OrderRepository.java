package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Optional<Order> findById(Long id);

	Optional<Order> findById(Integer integer);

}
