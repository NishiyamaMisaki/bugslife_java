package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {

	// // nameで曖昧検索ができるよう、クエリメソッドを定義する
	// public List<Shop> findByNameLike(String name);

	public List<Shop> findAll(Example<Shop> example);
}
