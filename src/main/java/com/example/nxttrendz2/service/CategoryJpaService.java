package com.example.nxttrendz2.service;

import com.example.nxttrendz2.repository.CategoryJpaRepository;
import com.example.nxttrendz2.repository.CategoryRepository;
import com.example.nxttrendz2.model.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryJpaService implements CategoryRepository {

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Override
    public ArrayList<Category> getCategories() {
        List<Category> categoryList = categoryJpaRepository.findAll();
        ArrayList<Category> categories = new ArrayList<>(categoryList);
        return categories;
    }

    @Override
    public Category getCategoryById(int id) {
        try {
            Category category = categoryJpaRepository.findById(id).get();
            return category;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Category addCategory(Category category) {
        categoryJpaRepository.save(category);
        return category;
    }

    @Override
    public Category updateCategory(int id, Category category) {
        try {
            Category newCategory = categoryJpaRepository.findById(id).get();
            if (category.getCategoryName() != null) {
                newCategory.setCategoryName(category.getCategoryName());
            }
            if (category.getCategoryDescription() != null) {
                newCategory.setCategoryDescription(category.getCategoryDescription());
            }
            categoryJpaRepository.save(newCategory);
            return newCategory;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteCategory(int id) {
        try {
            categoryJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

}