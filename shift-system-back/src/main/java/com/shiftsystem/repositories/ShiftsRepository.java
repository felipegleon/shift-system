package com.shiftsystem.repositories;

import com.shiftsystem.models.Shifts;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftsRepository extends ReactiveMongoRepository<Shifts, String> {
}
