package com.example.application.data.service.coordinate;

import com.example.application.data.entity.Coordinates;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CoordinatesService {

    private final CoordinateRepository repository;

    @Autowired
    public CoordinatesService(CoordinateRepository repository) {
        this.repository = repository;
    }

    public Optional<Coordinates> getCoordinates(Float x_axis, Float y_axis) {
        return Optional.of(repository.getCoordinates(x_axis, y_axis));
    }

    public Boolean createCoordinates(Float x_axis, Float y_axis, String name, String designation, String username) {
        return repository.createCoordinates(x_axis, y_axis, name, designation, username);
    }

    public Boolean updateCoordinates(Float x_axis, Float y_axis, String name, String designation, String username) {
        return repository.updateCoordinates(x_axis, y_axis, name, designation, username);
    }

    public void deleteCoordinates(String name, String designation, String username) {
        repository.deleteCoordinates(name, designation, username);
    }

}
