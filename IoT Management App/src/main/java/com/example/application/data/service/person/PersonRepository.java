package com.example.application.data.service.person;

import com.example.application.dao.Database;
import com.example.application.data.entity.Person;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    private Database database;

    public PersonRepository(Database database) {
        this.database = database;
    }

    // OK
    public Optional<Person> getPerson(String username) {
        database.jdbi.withHandle(handle -> handle.registerRowMapper(BeanMapper.factory(Person.PersonMapper.class)));
        List<Person> personsList = database.jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM PESSOA WHERE username = \'" + username + "\'").mapToBean(Person.class).list());

        if(personsList.isEmpty()) return Optional.empty();
        else return Optional.of(personsList.get(0));
    }

    // OK
    public List<Person> getAllPersons() {
        database.jdbi.withHandle(handle -> handle.registerRowMapper(BeanMapper.factory(Person.PersonMapper.class)));
        return database.jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM PESSOA").mapToBean(Person.class).list());
    }

    // OK
    public Boolean createPerson(String username, String password, String role, String email, Date birthDate, String profilePicture) {

        try {
            String insertStr = "INSERT INTO PESSOA (username, password, role, email, dateOfBirth, image) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = database.conn.prepareStatement(insertStr);
            java.sql.Date sqlDate = new java.sql.Date(birthDate.getTime());

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role.toLowerCase());
            stmt.setString(4, email);
            stmt.setDate(5,  sqlDate);
            stmt.setString(6, profilePicture);

            stmt.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    // OK
    public Boolean deletePerson(String username) {

        try {
            database.jdbi.withHandle(handle -> handle.createUpdate("delete from SINOTIC_WIDGET where username = \'" + username + "\'").execute());
            database.jdbi.withHandle(handle -> handle.createUpdate("delete from COORDENADAS where username = \'" + username + "\'").execute());
            database.jdbi.withHandle(handle -> handle.createUpdate("delete from SINOTICO where username = \'" + username + "\'").execute());
            database.jdbi.withHandle(handle -> handle.createUpdate("delete from PESSOA where username = \'" + username + "\'").execute());

            return true;
        }
        catch(Exception e) {
            database.jdbi.withHandle(handle -> handle.rollback());
        }

        return false;
    }

    // OK
    public Boolean updatePerson(String username, String password, String role, String email, Date birthDate, String profilePicture) {

        try {
            String insertStr = "UPDATE PESSOA SET password = ?, role = ?, email = ?, dateOfBirth = ?, image = ? where username = ?";
            PreparedStatement stmt = database.conn.prepareStatement(insertStr);

            java.sql.Date sqlDate = null;
            if(birthDate != null) sqlDate = new java.sql.Date(birthDate.getTime());

            stmt.setString(1, password);
            stmt.setString(2, role.toLowerCase());
            stmt.setString(3, email);
            stmt.setDate(4, sqlDate);
            stmt.setString(5, profilePicture);
            stmt.setString(6, username);

            stmt.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    // OK
    public int count() {
        return getAllPersons().size();
    }
}
