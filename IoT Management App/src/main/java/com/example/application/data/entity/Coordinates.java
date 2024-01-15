package com.example.application.data.entity;

import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class Coordinates extends AbstractEntity {

    private Float X_AXIS;
    private Float Y_AXIS;
    private String name;
    private String designation;
    private String username;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getX_AXIS() {
        return X_AXIS;
    }

    public void setX_AXIS(Float x_AXIS) {
        X_AXIS = x_AXIS;
    }

    public Float getY_AXIS() {
        return Y_AXIS;
    }

    public void setY_AXIS(Float y_AXIS) {
        Y_AXIS = y_AXIS;
    }


    public class CoordinatesMapper implements RowMapper<Coordinates> {

        @Override
        public Coordinates mapRow(ResultSet rs, int rowNum) throws SQLException {
            Coordinates coordinates = new Coordinates();

            coordinates.setX_AXIS(rs.getFloat("eixo_x"));
            coordinates.setY_AXIS(rs.getFloat("eixo_y"));
            coordinates.setName(rs.getString("nome"));
            coordinates.setDesignation(rs.getString("designacao"));
            coordinates.setUsername(rs.getString("username"));

            return coordinates;
        }
    }

}
