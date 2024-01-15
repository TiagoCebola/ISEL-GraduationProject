package com.example.application.data.entity;

import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class Synoptic  extends AbstractEntity {

    private String username;
    private String designation;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public class SynopticMapper implements RowMapper<Synoptic> {

        @Override
        public Synoptic mapRow(ResultSet rs, int rowNum) throws SQLException {
            Synoptic synoptic = new Synoptic();

            synoptic.setUsername(rs.getString("username"));
            synoptic.setDesignation(rs.getString("designacao"));

            return synoptic;
        }
    }


}
