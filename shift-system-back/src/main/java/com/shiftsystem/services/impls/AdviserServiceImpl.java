package com.shiftsystem.services.impls;

import com.shiftsystem.models.Adviser;
import com.shiftsystem.repositories.AdviserRepository;
import com.shiftsystem.services.AdviserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AdviserServiceImpl implements AdviserService {

    private final AdviserRepository adviserRepository;

    @Autowired
    public AdviserServiceImpl(AdviserRepository adviserRepository){
        this.adviserRepository = adviserRepository;
    }

    @Override
    public Flux<Adviser> getAllAdvisers() {
        return adviserRepository.findAll();
    }

    @Override
    public Mono<Adviser> getAdviserById(String id) {
        return adviserRepository.findById(id);
    }

    @Override
    public Mono<Adviser> saveAdviser(Adviser adviser) {
        return adviserRepository.save(adviser);
    }

    @Override
    public Mono<Void> deleteAdviser(String id) {
        return adviserRepository.deleteById(id);
    }
}
