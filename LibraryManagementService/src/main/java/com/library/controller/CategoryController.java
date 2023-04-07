package com.library.controller;

import com.library.entity.Category;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryByID(@PathVariable Long id) {
        if (categoryRepository.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id " + id + " is not existed !");
        } else {
            return ResponseEntity.ok().body(categoryRepository.findById(id));
        }
    }

    @DeleteMapping("/categories/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }

    //    Backend admin

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
        return "redirect:/categories";
    }

//    update category
//    @PutMapping("/categories/save/{id}")
//    public String updateCategory(Category category, RedirectAttributes attributes, @PathVariable String id) {
//        try{
//            categoryService.createCategory(category);
//            attributes.addFlashAttribute("success","Updated successfully");
//        }catch (DataIntegrityViolationException e){
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Failed to update because duplicate name");
//        }catch (Exception e){
//            e.printStackTrace();
//            attributes.addFlashAttribute("failed", "Error server");
//        }
//        return "redirect:/categories";
//    }


//    Frontend client
}

