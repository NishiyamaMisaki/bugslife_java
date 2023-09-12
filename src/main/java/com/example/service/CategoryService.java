package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.model.Category;
import com.example.model.CategoryProduct;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.repository.CategoryRepository;
import com.example.repository.CategoryProductRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryProductRepository categoryProductRepository;

	@Autowired
	private ProductRepository productRepository;

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	public Optional<Category> findOne(Long id) {
		return categoryRepository.findById(id);
	}

	@Transactional(readOnly = false)
	public Category save(Category entity) {
		return categoryRepository.save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(Category entity) {
		categoryRepository.delete(entity);
	}

	@Transactional
	public boolean deleteInsertCategoryProduct(Long categoryId, List<Long> productIds) {

		try {
			// カテゴリーIDに基づいて関連するCategoryProductを削除
			categoryProductRepository.deleteByCategoryId(categoryId);
			// 新しいCategoryProductを作成して保存
			insertCategoryProduct(categoryId, productIds);
			// トランザクションをコミット
			return true;
		} catch (Exception e) {
			// トランザクションをロールバック
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}

	private void insertCategoryProduct(Long categoryId, List<Long> productIds) {
		Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
		if (categoryOptional.isPresent()) {
			Category category = categoryOptional.get();

			for (Long productId : productIds) {
				Optional<Product> productOptional = productRepository.findById(productId);
				if (productOptional.isPresent()) {
					Product product = productOptional.get();

					// 新しいCategoryProductエンティティを作成
					CategoryProduct categoryProduct = new CategoryProduct();
					categoryProduct.setCategory(category);
					categoryProduct.setProduct(product);

					// CategoryProductエンティティを保存
					categoryProductRepository.save(categoryProduct);
				}
			}
		}
	}

}