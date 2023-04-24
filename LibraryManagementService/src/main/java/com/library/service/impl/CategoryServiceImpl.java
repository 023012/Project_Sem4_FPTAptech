package com.library.service.impl;

import com.library.entity.Category;
import com.library.repository.CategoryRepository;
import com.library.service.BookService;
import com.library.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private BookService bookService;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        categoryRepository.save(category);
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("Dang tai tat ca categories");
        return categoryRepository.findAll();
    }

    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();
        if (category == null) {
            return "Khong the tim thay Category " + id;
        } else {
            categoryRepository.delete(category);
            return "Category" + id + "has been deleted !";
        }
    }

    @Override
    public Category updateCategory(Category category) {
        if (category == null || category.getCategoryId() == null) {
            throw new IllegalArgumentException("Category or CategoryId cannot be null");
        }
        try {
            Category categoryUpdate = categoryRepository.findById(category.getCategoryId()).orElseThrow(() ->
                    new IllegalArgumentException("Category not found"));
            categoryUpdate.setName(category.getName());
            return categoryRepository.save(categoryUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
