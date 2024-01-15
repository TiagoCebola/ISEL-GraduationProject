package com.example.application.data.service.sinoticWidget;

import com.example.application.data.entity.SynopticWidget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SynopticWidgetService {

    private final SynopticWidgetRepository repository;

    @Autowired
    public SynopticWidgetService(SynopticWidgetRepository repository) {
        this.repository = repository;
    }

    public Optional<SynopticWidget> getSynopticWidget(String name, String username) {
        return Optional.of(repository.getSynopticWidget(name, username));
    }

    public Boolean createSynopticWidget(String name, String username, String wiotId) {
        return repository.createSynopticWidget(name, username, wiotId);
    }

    public Boolean updateSynopticWidget(String name, String username, String wiotId) {
        return repository.updateSynopticWidget(name, username, wiotId);
    }

    public Boolean deleteSynopticWidget(String name, String username, String wiotId) {
        return repository.deleteSynopticWidget(name, username, wiotId);
    }

    public List<SynopticWidget> listAllSynopticWidgets(String username) {
        return repository.getAllSynopticWidgets(username);
    }

}