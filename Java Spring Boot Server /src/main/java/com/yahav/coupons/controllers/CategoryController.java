package com.yahav.coupons.controllers;

import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.CategoryModel;
import com.yahav.coupons.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryModel addCategory(@RequestBody CategoryModel categoryModel) throws ServerException {
        return categoryService.addCategory(categoryModel);
    }

    @PutMapping
    public CategoryModel updateCategory(@RequestBody CategoryModel categoryModel) throws ServerException {
        return categoryService.updateCategory(categoryModel);
    }

    @GetMapping
    public List<CategoryModel> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public CategoryModel getCategory(@PathVariable(name = "id") long id) throws ServerException {
        return categoryService.getCategory(id);
    }

    @DeleteMapping("/{id}")
    public void removeCategory(@PathVariable(name = "id") long id) {
        categoryService.removeCategory(id);
    }
}
