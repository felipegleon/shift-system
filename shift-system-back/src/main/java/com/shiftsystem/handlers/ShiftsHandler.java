package com.shiftsystem.handlers;

import com.shiftsystem.models.Category;
import com.shiftsystem.models.Shifts;
import com.shiftsystem.services.ShiftsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
public class ShiftsHandler {

    private final String URI_SHIFT = "/api/shifts/";
    private ShiftsService shiftsService;


    @Autowired
    public ShiftsHandler(ShiftsService shiftsService, Validator validator){
        this.shiftsService = shiftsService;
    }

    public Mono<ServerResponse> getNextShift(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(shiftsService.getNextShift(), Shifts.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createShift(ServerRequest serverRequest){
        Mono<Shifts> shiftsMono = serverRequest.bodyToMono(Shifts.class);

        return shiftsService.createShift(shiftsMono).flatMap(mono ->
        {
            if(mono instanceof Shifts){
                Shifts newShifts = (Shifts) mono;
                return ServerResponse.created(URI.create(URI_SHIFT.concat(newShifts.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(mono));
            }
            List<FieldError> errors = (List<FieldError>) mono;
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errors, List.class);
        });
    }

    public Mono<ServerResponse> updateShift(ServerRequest serverRequest){
        return null;
    }

}
