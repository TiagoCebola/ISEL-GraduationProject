package com.example.application.data.service.synoptic;

import com.example.application.data.entity.Synoptic;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class SynopticService {

    private final SynopticRepository repository;

    @Autowired
    public SynopticService(SynopticRepository repository) {
        this.repository = repository;
    }

    public Optional<Synoptic> getSynoptic(String username, String designation) {
        return Optional.of(repository.getSynoptic(username, designation));
    }

    public Boolean createSynoptic(String username, String designation) {
        return repository.createSynoptic(username, designation);
    }

    public Boolean updateSynoptic(String username, String designation) {
        return repository.updateSynoptic(username, designation);
    }

    public void deleteSynoptic(String username, String designation) {
        repository.deleteSynoptic(username, designation);
    }

}
