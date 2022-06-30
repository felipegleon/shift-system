package com.shiftsystem.handlers;

import com.shiftsystem.models.Adviser;
import com.shiftsystem.services.AdviserService;
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
public class AdviserHandler {

    final static String URI_ADVISERS = "/api/advisers/";
    private AdviserService adviserService;
    private Validator validator;

    @Autowired
    public AdviserHandler(AdviserService adviserService, Validator validator){
        this.adviserService = adviserService;
        this.validator = validator;
    }

    public Mono<ServerResponse> getAllAdvisers(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(adviserService.getAllAdvisers(), Adviser.class);
    }

    public Mono<ServerResponse> getAdviserById(ServerRequest serverRequest){
        String id =  serverRequest.pathVariable("id");
        Mono<Adviser> adviserMono =  adviserService.getAdviserById(id);

        return adviserMono.flatMap(adviser ->
            ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(adviser))
                    .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> createAdviser(ServerRequest serverRequest){
        Mono<Adviser> adviserMono =  serverRequest.bodyToMono(Adviser.class);

        return adviserMono.flatMap(adviser -> {
            Errors errors =  new BeanPropertyBindingResult(adviser, Adviser.class.getName());
            validator.validate(adviser, errors);
            if(errors.hasErrors()){
              return Flux.fromIterable(errors.getFieldErrors())
                      .map(fieldError -> "The field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                      .collectList()
                      .flatMap(errorList -> ServerResponse.badRequest().body(BodyInserters.fromValue(errorList)));
            }
            return adviserService.saveAdviser(adviser).flatMap(adviserCreated ->
                    ServerResponse.created(URI.create(URI_ADVISERS.concat(adviserCreated.getId())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromValue(adviserCreated))
            );
        });
    }

    public Mono<ServerResponse> editAdviser(ServerRequest serverRequest){
        String id = serverRequest.pathVariable("id");
        Mono<Adviser> adviserMono =  serverRequest.bodyToMono(Adviser.class);
        Mono<Adviser> adviserMonoDB = adviserService.getAdviserById(id);

        return adviserMonoDB.zipWith(adviserMono, (adviserDB, adviser) ->{
            /*Errors errors =  new BeanPropertyBindingResult(adviser, Adviser.class.getName());
            validator.validate(adviser, errors);
            if(errors.hasErrors()){
                return Flux.fromIterable(errors.getFieldErrors())
                        .map(fieldError -> "The field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collectList()
                        .flatMap(errorList -> ServerResponse.badRequest().body(BodyInserters.fromValue(errorList)));
            }*/

            adviser.setId(adviserDB.getId());
            return adviser;
        }).flatMap(adviserToSave -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(adviserService.saveAdviser((Adviser) adviserToSave), Adviser.class)

        ).switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> deleteAdviser(ServerRequest serverRequest){
        String id =  serverRequest.pathVariable("id");
        return adviserService.deleteAdviser(id).then(ServerResponse.noContent().build());
    }

}
