package com.library.controller;

import com.library.entity.Category;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@EnableScheduling
@Slf4j
@Controller
//@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;


//    @GetMapping("/list")
//    public List<Category> getAllCategories() {
//        return categoryService.getAllCategories();
//    }


    //    Backend admin

    @GetMapping("/admin/categories")
    public String category(Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Categories");
        return "admin/category";
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryByID(@PathVariable Long id) {
        if (categoryRepository.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id " + id + " is not existed !");
        } else {
            return ResponseEntity.ok().body(categoryRepository.findById(id));
        }
    }

//    add category
    @RequestMapping(value = "/category/new")
    public String addForm(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        model.addAttribute("title", "Add new category");
        return "admin/category-add";
    }

    @PostMapping("/category/add")
    public String createCategory(@Validated @ModelAttribute("category")
                                       Category category, BindingResult result) {
        if (result.hasErrors()){
            return "redirect:/category/new";
        }
        categoryService.createCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long category_id, Model model) {
        Category category = categoryRepository.findById(category_id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Category Id:" + category_id));

        model.addAttribute("category", category);
        model.addAttribute("title","Edit Category");
        return "admin/category-edit";
    }

    @PostMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable("id") long category_id, @Valid Category category,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            category.setCategoryId(category_id);
            return "admin/category-edit";
        }

        Category existingCategory = categoryRepository.findById(category_id).orElse(null);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Invalid Category Id:" + category_id);
        }

        existingCategory.setName(category.getName());
        categoryService.updateCategory(existingCategory);

        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") long id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        categoryRepository.delete(category);
        return "redirect:/admin/categories";
    }
}

