package com.example.nxttrendz2.service;

import com.example.nxttrendz2.repository.ProductJpaRepository;
import com.example.nxttrendz2.repository.ProductRepository;
import com.example.nxttrendz2.model.Product;
import com.example.nxttrendz2.repository.CategoryJpaRepository;
import com.example.nxttrendz2.model.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductJpaService implements ProductRepository {

	@Autowired
	private ProductJpaRepository productJpaRepsoitory;

	@Autowired
	private CategoryJpaRepository categoryJpaRepository;

	@Override
	public ArrayList<Product> getProducts() {
		List<Product> productList = productJpaRepsoitory.findAll();
		ArrayList<Product> products = new ArrayList<>(productList);
		return products;
	}

	@Override
	public Product getProductById(int id) {
		try {
			Product product = productJpaRepsoitory.findById(id).get();
			return product;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Product addProduct(Product product) {
		Category category = product.getCategory();
		int id = category.getCategoryId();
		try {
			category = categoryJpaRepository.findById(id).get();
			product.setCategory(category);
			productJpaRepsoitory.save(product);
			return product;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public Product updateProduct(int id, Product product) {
		try {
			Product newProduct = productJpaRepsoitory.findById(id).get();
			if (product.getCategory() != null) {
				int categoryId = product.getCategory().getCategoryId();
				Category newCategory = categoryJpaRepository.findById(categoryId).get();
				newProduct.setCategory(newCategory);
			}
			if (product.getProductName() != null) {
				newProduct.setProductName(product.getProductName());
			}
			if (product.getProductDescription() != null) {
				newProduct.setProductDescription(product.getProductDescription());
			}
			if (product.getPrice() != 0) {
				newProduct.setPrice(product.getPrice());
			}
			productJpaRepsoitory.save(newProduct);
			return newProduct;

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteProduct(int id) {
		try {
			productJpaRepsoitory.deleteById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);

	}

	@Override
	public Category getProoductCategory(int id) {
		try {
			Product product = productJpaRepsoitory.findById(id).get();
			Category category = product.getCategory();
			return category;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
