package com.example.application.data.service.sinoticWidget;

import com.example.application.dao.Database;
import com.example.application.data.entity.SynopticWidget;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class SynopticWidgetRepository {

    private Database database;

    public SynopticWidgetRepository(Database database) {
        this.database = database;
    }

    public SynopticWidget getSynopticWidget(String name, String username) {
        database.jdbi.registerRowMapper(BeanMapper.factory(SynopticWidget.SynopticMapper.class));
        return database.jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM SINOTIC_WIDGET WHERE name = \'" + name + "\'  AND username = \'" + username + "\'")
                .mapToBean(SynopticWidget.class).list().get(0));
    }


    public List<SynopticWidget> getAllSynopticWidgets(String username) {
        database.jdbi.withHandle(handle -> handle.registerRowMapper(BeanMapper.factory(SynopticWidget.SynopticMapper.class)));
        return database.jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM SINOTIC_WIDGET WHERE username = \'" + username + "\'")
                .mapToBean(SynopticWidget.class).list());
    }

    public Boolean createSynopticWidget(String name, String username, String wiotId) {

        try {
            String insertStr = "INSERT INTO SINOTIC_WIDGET (name, designation, username, wiotId) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = database.conn.prepareStatement(insertStr);

            stmt.setString(1, name);
            stmt.setString(2, "SYNOPTIC 1");
            stmt.setString(3, username);
            stmt.setObject(4, UUID.fromString(wiotId));

            stmt.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            database.jdbi.withHandle(handle -> handle.rollback());
            throwables.printStackTrace();
        }

        return false;
    }


    public Boolean updateSynopticWidget(String name, String username, String wiotId) {

        try {
            String insertStr = "UPDATE SINOTIC_WIDGET SET wiotId = ? where name = ?, username = ?";
            PreparedStatement stmt = database.conn.prepareStatement(insertStr);
            
            stmt.setString(1, wiotId);
            stmt.setString(2, name);
            stmt.setString(3, username);
            

            stmt.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }



    public Boolean deleteSynopticWidget(String name, String username, String wiotId) {

        try {

            String deleteStr = "DELETE from SINOTIC_WIDGET where name = ? AND designation = ? AND  username = ?  AND wiotId = ?";
            PreparedStatement stmt = database.conn.prepareStatement(deleteStr);

            stmt.setString(1, name);
            stmt.setString(2, "SYNOPTIC 1");
            stmt.setString(3, username);
            stmt.setObject(4, UUID.fromString(wiotId));

            stmt.executeUpdate();
            return true;
        }
        catch(Exception e) {
            database.jdbi.withHandle(handle -> handle.rollback());
        }

        return false;
    }



}
