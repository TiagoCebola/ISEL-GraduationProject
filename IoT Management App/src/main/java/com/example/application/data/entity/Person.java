package com.example.application.data.entity;

import com.example.application.data.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.component.html.Image;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


@Entity
public class Person extends AbstractEntity {

    private String username;

    @JsonIgnore
    private String password;
    private String role;

    private String image;
    private Image imgPreview;
    private LocalDate dateOfBirth;
    private String email;

    public class PersonMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person person = new Person();
            String role = "";

            String tipo_utilizador = rs.getString("role");
            if(tipo_utilizador.equalsIgnoreCase("admin")) role = Role.ADMIN.name();
            else if(tipo_utilizador.equalsIgnoreCase("user")) role = Role.USER.name();

            person.setUsername(rs.getString("username"));
            person.setPassword(rs.getString("password"));
            person.setRole(role);

            Image m = new Image(rs.getString("image"), "User");
            person.setImgPreview(m);
            person.setImage(m.getSrc());

            person.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
            person.setEmail(rs.getString("email"));


            return person;
        }
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public Image getImgPreview() {
        return imgPreview;
    }
    public void setImgPreview(Image imgPreview) {
        this.imgPreview = imgPreview;
    }


    public String getImage()  {
        return image;
    }

    public String getImageRenderer()  {

        try {
         //   ImageComponent comp = new ImageComponent();
           // ResponseEntity<Image> image = comp.getImageById(username);

            return image;

        }
        catch (Exception e) {
            return null;
        }

    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
