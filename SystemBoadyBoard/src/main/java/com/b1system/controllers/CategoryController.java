package com.b1system.controllers;

import com.b1system.models.createDtos.CategoryCreateDTO;
import com.b1system.models.dtos.CategoryDTO;
import com.b1system.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CategoryDTO createCategory(@Validated @RequestBody CategoryCreateDTO body) {
        return categoryService.create(body);
    }

    @GetMapping("/get/{eventId}")
    public List<CategoryDTO> createCategory(@Validated @RequestBody Integer eventId) {
        return categoryService.getAllByEventId(eventId);
    }

}
