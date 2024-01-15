package com.example.application.data.service.widget_iot;

import com.example.application.dao.Database;
import com.example.application.data.entity.Widget_IoT;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class Widget_IoTRepository {

    private Database database;

    public Widget_IoTRepository(Database database) {
        this.database = database;
    }

    // OK
    public Optional<Widget_IoT> getWidget_IoT(String wiotId) {
        database.jdbi.withHandle(handle -> handle.registerRowMapper(BeanMapper.factory(Widget_IoT.Widget_IotMapper.class)));
        List<Widget_IoT> widgetList = database.jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM WIDGET_IOT WHERE wiotId = \'" + wiotId + "\'")
                .mapToBean(Widget_IoT.class).list());

        if(widgetList.isEmpty()) return Optional.empty();
        else return Optional.of(widgetList.get(0));
    }

    // OK
    public List<Widget_IoT> getAllWidgets() {
        database.jdbi.withHandle(handle -> handle.registerRowMapper(BeanMapper.factory(Widget_IoT.Widget_IotMapper.class)));
        return database.jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM WIDGET_IOT")
                .mapToBean(Widget_IoT.class).list());
    }

    // OK
    public Boolean createWidget_IoT(String iotType, String comun_proto, String svgImage) {

        try {
            String insertStr = "INSERT INTO WIDGET_IOT (iotType, comun_proto, svgImage) VALUES (?, ?, ?)";
            PreparedStatement stmt = database.conn.prepareStatement(insertStr);

            stmt.setString(1, iotType);
            stmt.setString(2, comun_proto);
            stmt.setString(3, svgImage);

            stmt.executeUpdate();
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    // OK
    public Boolean updateWidget_IoT(String wiotId, String iotType, String comun_proto, String svgImage) {

        try {
            String insertStr = "UPDATE WIDGET_IOT SET iotType = ?, comun_proto = ?, svgImage = ? where wiotId = ?";
            PreparedStatement stmt = database.conn.prepareStatement(insertStr);

            UUID uuid = UUID.fromString(wiotId);

            stmt.setString(1, iotType);
            stmt.setString(2, comun_proto);
            stmt.setString(3, svgImage);
            stmt.setObject(4, uuid);

            stmt.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    // OK
    public Boolean deleteWidget_IoT(String wiotId) {

        try {
            database.jdbi.withHandle(handle ->
                    handle.createUpdate("DELETE from WIDGET_IOT WHERE wiotId = \'" + wiotId + "\'").execute());
            return true;
        }
        catch(Exception e) {
            database.jdbi.withHandle(handle -> handle.rollback());
        }

        return false;
    }

    // OK
    public int count() {
        return getAllWidgets().size();
    }
}
