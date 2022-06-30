package com.shiftsystem.services;

import com.shiftsystem.models.Adviser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdviserService {

    Flux<Adviser> getAllAdvisers();
    Mono<Adviser> getAdviserById(String id);
    Mono<Adviser> saveAdviser(Adviser adviser);
    Mono<Void> deleteAdviser(String id);

}
