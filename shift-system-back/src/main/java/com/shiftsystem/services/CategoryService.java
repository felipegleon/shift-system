package com.shiftsystem.services;

import com.shiftsystem.models.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<Category> getAllCategories();
    Mono<Category> getCategoryById(String id);
    Mono<Category> saveCategory(Category category);
    Mono<Void> deleteCategory(String id);
}
