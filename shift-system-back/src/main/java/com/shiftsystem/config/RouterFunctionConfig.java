package com.shiftsystem.config;


import com.shiftsystem.handlers.CategoryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> routesCategories (CategoryHandler categoryHandler){

        return RouterFunctions.route(
                GET("/api/categories"), categoryHandler::getAllCategories)
                .andRoute(GET("/api/categories/{id}"), categoryHandler::getCategoryById)
                .andRoute(POST("/api/categories"), categoryHandler::createCategory)
                .andRoute(PUT("/api/categories/{id}"), categoryHandler::editCategory)
                .andRoute(DELETE("/api/categories/{id}"), categoryHandler::deleteCategory);
    }
}
