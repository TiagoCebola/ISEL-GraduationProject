package com.example.application.data.entity;

import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class Widget_IoT  extends AbstractEntity {

    private String wiotId;
    private String iotType;
    private String comun_proto;
    private String svgImage;

    public class Widget_IotMapper implements RowMapper<Widget_IoT> {

        @Override
        public Widget_IoT mapRow(ResultSet rs, int rowNum) throws SQLException {
            Widget_IoT widget = new Widget_IoT();


            widget.setWiotId(rs.getString("wiotId"));
            widget.setIotType(rs.getString("iotType"));
            widget.setComun_proto(rs.getString("comun_proto"));
            widget.setSvgImage(rs.getString("svgImage"));

            return widget;
        }
    }

    public String getWiotId() {
        return wiotId;
    }
    public void setWiotId(String wiotId) {
        this.wiotId = wiotId;
    }

    public String getIotType() {
        return iotType;
    }
    public void setIotType(String iotType) {
        this.iotType = iotType;
    }

    public String getComun_proto() {
        return comun_proto;
    }
    public void setComun_proto(String comun_proto) {
        this.comun_proto = comun_proto;
    }

    public String getSvgImage() {
        return svgImage;
    }
    public void setSvgImage(String svgImage) {
        this.svgImage = svgImage;
    }

}