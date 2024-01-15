package com.example.application;

import com.example.application.dao.Database;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.sql.SQLException;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Theme(value = "iotmanager")
@PWA(name = "IoT Manager", shortName = "IoT Manager", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    public static Database db;
    public Application() {
        this.db = new Database();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        try {
            db.startDatabse();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
