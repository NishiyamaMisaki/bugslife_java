package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Tax;

@Repository
public interface TaxListRepository extends JpaRepository<Tax, Integer> {

	// save()
	Tax save(Tax entity);

	// delete()
	void delete(Tax entity);
}
