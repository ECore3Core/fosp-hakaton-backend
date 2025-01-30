package com.tinkov.profile.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinkov.profile.entity.Category;
import com.tinkov.profile.service.CategoryService;
import com.tinkov.profile.service.ExternalAuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExternalAuthService externalAuthService;
    
    private boolean validateToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authorizationHeader.substring(7);
        return externalAuthService.validateToken(token);
    }

    @PostMapping("/create-category")
    public ResponseEntity<String> createCategory(@RequestBody Category category, HttpServletRequest request) {
        if (!validateToken(request)){
            throw new RuntimeException("Token is invalid.");
        }
        categoryService.createCategory(category);
        return ResponseEntity.ok("Category created successfully");
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity<List<Category>> getAllCategories(HttpServletRequest request) {
        if (!validateToken(request)){
            throw new RuntimeException("Token is invalid.");
        }
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> getCategoryById(@PathVariable Long id, HttpServletRequest request) {
        if (!validateToken(request)){
            throw new RuntimeException("Token is invalid.");
        }
        Optional<Category> categories = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category, HttpServletRequest request) {
        if (!validateToken(request)){
            throw new RuntimeException("Token is invalid.");
        }
        Category categories = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id, HttpServletRequest request) {
        if (!validateToken(request)){
            throw new RuntimeException("Token is invalid.");
        }
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
