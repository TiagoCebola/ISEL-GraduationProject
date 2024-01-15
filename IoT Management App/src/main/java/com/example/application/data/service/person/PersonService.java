package com.example.application.data.service.person;

import com.example.application.data.entity.Person;
import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Optional<Person> getPerson(String username) {
        return repository.getPerson(username);
    }

    public Boolean createPerson(String username, String password, String role, String email, Date birthDate, String profilePicture) {
        String encodedPassword = passwordEncoder.encode(password);
        return repository.createPerson(username, encodedPassword, role, email, birthDate, profilePicture);
    }

    public Boolean updatePerson(String username, String password, String role, String email, Date birthDate, String profilePicture) {
        String encodedPassword = passwordEncoder.encode(password);
        return repository.updatePerson(username, encodedPassword, role, email, birthDate, profilePicture);
    }

    public Boolean deletePerson(String username) {
        return repository.deletePerson(username);
    }

    public List<Person> listAllPersons() {

        return repository.getAllPersons();

    }

    public int countPersons() {
        return repository.getAllPersons().size();
    }

}
