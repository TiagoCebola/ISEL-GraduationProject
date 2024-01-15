package com.example.application.security;

import com.example.application.Application;
import com.example.application.data.entity.Person;
import com.example.application.data.service.person.PersonRepository;
import com.example.application.data.service.person.PersonService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class AuthenticatedPerson {

    private PersonRepository repo =  new PersonRepository(Application.db);
    private PersonService personService = new PersonService(repo);


    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }

    public Optional<Person> get() {
        return getAuthentication().map(authentication -> personService.getPerson(authentication.getName()).get());
    }


    public void logout() {
        UI.getCurrent().getPage().setLocation(SecurityConfiguration.LOGOUT_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
