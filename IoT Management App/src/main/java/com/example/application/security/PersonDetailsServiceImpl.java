package com.example.application.security;

import com.example.application.Application;
import com.example.application.data.entity.Person;
import com.example.application.data.service.person.PersonRepository;
import com.example.application.data.service.person.PersonService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class PersonDetailsServiceImpl implements UserDetailsService {

    private PersonRepository repo =  new PersonRepository(Application.db);
    private PersonService personService = new PersonService(repo);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.getPerson(username).get();
        if (person == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(person.getUsername(), person.getPassword(),
                   getAuthorities(person));
        }
    }

    private static List<GrantedAuthority> getAuthorities(Person person) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + person.getRole().toUpperCase()));
    }

}
