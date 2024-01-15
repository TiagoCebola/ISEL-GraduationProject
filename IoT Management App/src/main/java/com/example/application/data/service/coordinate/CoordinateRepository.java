package com.example.application.data.service.coordinate;

import com.example.application.dao.Database;
import com.example.application.data.entity.Coordinates;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class CoordinateRepository extends Database {

    public Coordinates getCoordinates(Float x_axis, Float y_axis) {
        jdbi.registerRowMapper(BeanMapper.factory(Coordinates.CoordinatesMapper.class));
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM COORDENADAS WHERE eixo_x = " + x_axis + " AND eixo_y = " + y_axis)
                    .mapToBean(Coordinates.class).list().get(0));
    }

    public Boolean createCoordinates(Float x_axis, Float y_axis, String name, String designation, String username) {

        try {
            String insertStr = "INSERT INTO COORDENADAS (eixo_x, eixo_y, name, designation, username,  VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertStr);

            stmt.setFloat(1, x_axis);
            stmt.setFloat(2, y_axis);
            stmt.setString(3, name);
            stmt.setString(4, designation);
            stmt.setString(5, username);

            stmt.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }



    public Boolean updateCoordinates(Float x_axis, Float y_axis, String name, String designation, String username) {

        try {
            String insertStr = "UPDATE COORDENADAS SET eixo_x = ?, eixo_y = ? where name = ? and designation = ? and username = ?";
            PreparedStatement stmt = conn.prepareStatement(insertStr);

            stmt.setFloat(1, x_axis);
            stmt.setFloat(2, y_axis);
            stmt.setString(3, name);
            stmt.setString(4, designation);
            stmt.setString(5, username);

            stmt.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }



    public Boolean deleteCoordinates(String name, String designation, String username) {

        try {
            jdbi.withHandle(handle ->
                    handle.createUpdate("delete from COORDENADAS where name = " + name + " and designation = " + designation  +  " and username = " + username).execute());

            return true;
        }
        catch(Exception e) {
            jdbi.withHandle(handle -> handle.rollback());
        }

        return false;
    }



}
