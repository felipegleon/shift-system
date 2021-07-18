package com.shiftsystem.services;

import com.shiftsystem.models.Adviser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AdviserService {

    Flux<Adviser> getAllAdvisers();
    Mono<Adviser> getAdviserById(String id);
    Mono<Adviser> createAdviser(Adviser adviser);
    Mono<Adviser> editAdviser(String id, Adviser adviser);
    Mono<Adviser> deleteAdviser(String id);

}
