package com.shiftsystem.services.impls;

import com.shiftsystem.models.Shifts;
import com.shiftsystem.repositories.ShiftsRepository;
import com.shiftsystem.services.ShiftsService;
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

import java.net.URI;

@Service
public class ShiftsServiceImpl implements ShiftsService {

    private ShiftsRepository shiftsRepository;
    private Validator validator;

    @Autowired
    public ShiftsServiceImpl(ShiftsRepository shiftsRepository, Validator validator){
        this.shiftsRepository = shiftsRepository;
        this.validator =  validator;
    }

    @Override
    public Mono<Shifts> getNextShift() {
        return null;
    }

    @Override
    public Mono<?> createShift (Mono<Shifts> shiftsMono){
        return shiftsMono.flatMap(shift -> {
            Errors errors = new BeanPropertyBindingResult(shift, Shifts.class.getName());
            validator.validate(shift, errors);
            if(errors.hasErrors()){
                return Flux.fromIterable(errors.getFieldErrors())
                        .map(fieldError -> "The field " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collectList()
                        .flatMap(errorsList -> ServerResponse.badRequest().body(BodyInserters.fromValue(errorsList)));
            }
            return shiftsRepository.save(shift);
        });
    }

    @Override
    public Mono<Shifts> saveShift(Shifts shift) {
        return shiftsRepository.save(shift);
    }
}
