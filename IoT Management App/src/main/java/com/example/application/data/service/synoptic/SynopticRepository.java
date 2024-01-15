package com.example.application.data.service.synoptic;

import com.example.application.dao.Database;
import com.example.application.data.entity.Synoptic;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class SynopticRepository  {

    private Database database;

    public SynopticRepository(Database database) {
        this.database = database;
    }

    public Synoptic getSynoptic(String username, String designation) {
        database.jdbi.registerRowMapper(BeanMapper.factory(Synoptic.SynopticMapper.class));
        return database.jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM SINOTICO WHERE designation = " + designation + " AND username = " + username)
                .mapToBean(Synoptic.class).list().get(0));
    }

    public Boolean createSynoptic(String username, String designation) {

        try {
            String insertStr = "INSERT INTO SINOTICO (designation, username VALUES (?, ?)";
            PreparedStatement stmt = database.conn.prepareStatement(insertStr);

            stmt.setString(1, designation);
            stmt.setString(2, username);

            stmt.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }


    // FALTA POR PLICAS ESQUECI ME COMO FAZ
    public Boolean updateSynoptic(String username, String designation) {

        try {
            String insertStr = "UPDATE SINOTICO SET designation = ? where username = ?";
            PreparedStatement stmt = database.conn.prepareStatement(insertStr);

            stmt.setString(1, designation);
            stmt.setString(2, username);

            stmt.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }


    // FALTA POR PLICAS ESQUECI ME COMO FAZ
    public Boolean deleteSynoptic(String username, String designation) {

        try {
            database.jdbi.withHandle(handle ->
                    handle.createUpdate("delete from SINOTICO where designation = " + designation  +  " and username = " + username).execute());

            return true;
        }
        catch(Exception e) {
            database.jdbi.withHandle(handle -> handle.rollback());
        }

        return false;
    }



}
