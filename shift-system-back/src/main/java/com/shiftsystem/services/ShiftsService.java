package com.shiftsystem.services;

import com.shiftsystem.models.Shifts;
import reactor.core.publisher.Mono;

public interface ShiftsService {

    Mono<Shifts> getNextShift();

    public Mono<?> createShift (Mono<Shifts> shiftsMono);
    Mono<Shifts> saveShift(Shifts shift);

}
