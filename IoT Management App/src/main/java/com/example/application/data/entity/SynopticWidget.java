package com.example.application.data.entity;

import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class SynopticWidget extends AbstractEntity {

    private String name;
    private String wiotId;
    private String designacaoSinotico;
    private String username;
    private int restID;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getWiotId() { return wiotId; }

    public void setWiotId(String wiotId) { this.wiotId = wiotId; }

    public String getDesignacaoSinotico() { return designacaoSinotico; }

    public void setDesignacaoSinotico(String designacaoSinotico) { this.designacaoSinotico = designacaoSinotico; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public int getRestID() {
        return restID;
    }

    public void setRestID(int restID) {
        this.restID = restID;
    }

    public class SynopticMapper implements RowMapper<SynopticWidget> {

        @Override
        public SynopticWidget mapRow(ResultSet rs, int rowNum) throws SQLException {
            SynopticWidget sinoticWidget = new SynopticWidget();

            sinoticWidget.setName(rs.getString("nome"));
            sinoticWidget.setWiotId(rs.getString("wiotId"));
            sinoticWidget.setDesignacaoSinotico(rs.getString("designacao"));
            sinoticWidget.setUsername(rs.getString("username"));
            sinoticWidget.setRestID(rs.getInt("restID"));

            return sinoticWidget;
        }
    }

}