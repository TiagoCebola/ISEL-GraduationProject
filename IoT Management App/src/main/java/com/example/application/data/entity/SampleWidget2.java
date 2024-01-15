package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
public class SampleWidget2 extends AbstractEntity {

    @NotEmpty
    private String image;

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @Email
    @NotEmpty
    private String email = "";

    @NotEmpty
    private String phone = "";

    private LocalDate dateOfBirth;

    private String status;


    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
