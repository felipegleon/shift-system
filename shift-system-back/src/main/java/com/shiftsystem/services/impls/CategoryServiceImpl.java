package com.shiftsystem.services.impls;

import com.shiftsystem.models.Category;
import com.shiftsystem.repositories.CategoryRepository;
import com.shiftsystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryRepository categoryRepository;
    private Validator validator;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, Validator validator){
        this.categoryRepository = categoryRepository;
        this.validator = validator;
    }

    @Override
    public Flux<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Mono<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Mono<?> createCategory(Mono<Category> categoryMono) {
        return categoryMono.flatMap(category -> {
            Errors errors = new BeanPropertyBindingResult(category, Category.class.getName());
            validator.validate(category, errors);
            if (errors.hasErrors()) {
                return Flux.fromIterable(errors.getFieldErrors())
                        .map(fieldError -> "The field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collectList();
            }
            category.setCurrentConsecutive(0L);
            return categoryRepository.save(category);
        });
    }

    @Override
    public Mono<Category> editCategory(String id,  Mono<Category> categoryMono){
        Mono<Category> categoryMonoDb = getCategoryById(id);
        return categoryMonoDb.hasElement().flatMap(flag -> {
           if(flag){
               return categoryMonoDb.zipWith(categoryMono, (categoryDb, category) -> {
                   categoryDb.setName(category.getName());
                   categoryDb.setWeight(category.getWeight());
                   return categoryDb;
               }).flatMap(category -> categoryRepository.save(category));
           }
           return Mono.empty();
        });
    }

    @Override
    public Mono<Void> deleteCategory(String id) {
        return categoryRepository.deleteById(id);
    }
}
