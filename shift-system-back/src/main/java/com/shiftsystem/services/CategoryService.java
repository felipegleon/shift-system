package com.shiftsystem.services;

import com.shiftsystem.models.Category;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Flux<Category> getAllCategories();
    Mono<Category> getCategoryById(String id);

    Mono<?> createCategory(Mono<Category> categoryMono);
    Mono<Category> editCategory(String id,  Mono<Category> categoryMono);
    Mono<Void> deleteCategory(String id);
}
