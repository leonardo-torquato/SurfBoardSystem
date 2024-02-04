package com.b1system.controllers;

import com.b1system.models.CategoryDTO;
import com.b1system.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public CategoryDTO createCategory(@Validated @RequestBody CategoryDTO body) {
        System.out.println("AQUIII");
        return categoryService.create(body);
    }

}
