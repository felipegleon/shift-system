package com.shiftsystem.handlers;

import com.shiftsystem.models.Category;
import com.shiftsystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CategoryHandler {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Validator validator;

    public Mono<ServerResponse> getAllCategories(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.getAllCategories(), Category.class);
    }

    public Mono<ServerResponse> getCategoryById (ServerRequest serverRequest){
        String id =  serverRequest.pathVariable("id");
        return categoryService.getCategoryById(id).flatMap(category ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(category))
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> createCategory (ServerRequest serverRequest){
        Mono<Category> categoryMono = serverRequest.bodyToMono(Category.class);
        return categoryMono.flatMap(category ->{
            Errors errors = new BeanPropertyBindingResult(category, Category.class.getName());
            validator.validate(category, errors);
            if(errors.hasErrors()){
                return Flux.fromIterable(errors.getFieldErrors())
                        .map(fieldError -> "The field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collectList()
                        .flatMap(errorsList -> ServerResponse.badRequest().body(BodyInserters.fromValue(errorsList)));
            }
            return categoryService.saveCategory(category).flatMap(cat ->
                    ServerResponse.created(URI.create("/api/categories/".concat(cat.getId())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(cat))
            );
        });
    }

    public Mono<ServerResponse> editCategory(ServerRequest serverRequest) {
        Mono<Category> categoryMono = serverRequest.bodyToMono(Category.class);
        String id =  serverRequest.pathVariable("id");
        Mono<Category> categoryMonoDb = categoryService.getCategoryById(id);

        return categoryMonoDb.zipWith(categoryMono, (categoryDb, category) -> {
            categoryDb.setName(category.getName());
            categoryDb.setWeight(category.getWeight());
            return categoryDb;
        }).flatMap(p -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(categoryService.saveCategory(p), Category.class)
        ).switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteCategory(ServerRequest serverRequest) {
        String id =  serverRequest.pathVariable("id");
        return categoryService.deleteCategory(id).then(ServerResponse.noContent().build());
    }
}
