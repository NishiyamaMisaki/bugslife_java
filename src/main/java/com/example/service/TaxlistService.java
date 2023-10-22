package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Tax;
import com.example.repository.TaxListRepository;

@Service
public class TaxlistService {

	@Autowired
	private TaxListRepository taxlistRepository;

	public List<Tax> findAll() {
		return taxlistRepository.findAll();
	}

	public Optional<Tax> findOne(Integer id) {
		return taxlistRepository.findById(id);
	}

	public Tax save(Tax entity) {
		return taxlistRepository.save(entity);
	}

	public void delete(Tax entity) {
		taxlistRepository.delete(entity);
	}
}
