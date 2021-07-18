package com.shiftsystem.repositories;

import com.shiftsystem.models.Adviser;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviserRepository extends ReactiveMongoRepository<Adviser, String> {
}
