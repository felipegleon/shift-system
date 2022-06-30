package com.shiftsystem.handlers;

import com.shiftsystem.models.Category;
import com.shiftsystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class CategoryHandler {

    final static String URI_CATEGORY = "/api/categories/";
    private CategoryService categoryService;

    @Autowired
    public CategoryHandler(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Mono<ServerResponse> getAllCategories(ServerRequest serverRequest) {
        Flux<Category> categoryFlux = categoryService.getAllCategories();
        return categoryFlux.hasElements().flatMap(flag -> {
            if (flag) {
                return ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(categoryFlux, Category.class);
            }
            return ServerResponse.notFound().build();
        });
    }

    public Mono<ServerResponse> getCategoryById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        Mono<Category> categoryMono = categoryService.getCategoryById(id);
        return categoryMono.hasElement().flatMap(flag -> {
            if (flag) {
                return ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(categoryMono, Category.class);
            }
            return ServerResponse.notFound().build();
        });
    }

    public Mono<ServerResponse> createCategory(ServerRequest serverRequest) {
        Mono<Category> categoryMono = serverRequest.bodyToMono(Category.class);
        return categoryService.createCategory(categoryMono).flatMap(mono ->
        {
            if (mono instanceof Category) {
                Category newCategory = (Category) mono;
                return ServerResponse.created(URI.create(URI_CATEGORY.concat(newCategory.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(mono));
            }
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(mono));
        });
    }

    public Mono<ServerResponse> editCategory(ServerRequest serverRequest) {
        Mono<Category> categoryMono = serverRequest.bodyToMono(Category.class);
        String id = serverRequest.pathVariable("id");
        Mono<Category> categoryMonoUpdated = categoryService.editCategory(id, categoryMono);
        return categoryMonoUpdated.hasElement()
                .flatMap(flag -> {
                    if (flag) {
                        return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(categoryMonoUpdated, Category.class);
                    }
                    return ServerResponse.notFound().build();
                });
    }

    public Mono<ServerResponse> deleteCategory(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return categoryService.deleteCategory(id)
                .then(ServerResponse.noContent().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
