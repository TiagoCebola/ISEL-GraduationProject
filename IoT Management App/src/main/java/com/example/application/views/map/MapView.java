package com.example.application.views.map;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.View;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.map.configuration.style.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import javax.annotation.security.PermitAll;

@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
@PermitAll
public class MapView extends VerticalLayout {

    private Map map = new Map();

    public MapView() {
        setSizeFull();
        setPadding(false);
        map.getElement().setAttribute("theme", "borderless");

        View view = map.getView();
        view.setCenter(Coordinate.fromLonLat(-9.1333300, 38.7166700));
        view.setZoom(6);
        addAndExpand(map);


        // Add marker for Vaadin HQ, using default marker image
        Coordinate vaadinHqCoordinates = Coordinate.fromLonLat(-9.1333300, 38.7166700);
        MarkerFeature vaadinHq = new MarkerFeature(vaadinHqCoordinates);
        map.getFeatureLayer().addFeature(vaadinHq);

        // Add marker for Vaadin office in Germany, using image from URL
        Coordinate germanOfficeCoordinates = Coordinate.fromLonLat(13.45489, 52.51390);
        Icon.Options germanFlagIconOptions = new Icon.Options();
        germanFlagIconOptions.setSrc("images/empty-plant.png");
        Icon germanFlagIcon = new Icon(germanFlagIconOptions);
        MarkerFeature germanOffice = new MarkerFeature(germanOfficeCoordinates, germanFlagIcon);
        map.getFeatureLayer().addFeature(germanOffice);

        // Add marker for Vaadin office in the US, using image from a StreamResource
        Coordinate usOfficeCoordinates = Coordinate.fromLonLat(-121.92163, 37.36821);
        StreamResource streamResource = new StreamResource("us-flag.png",
                () -> getClass().getResourceAsStream("/META-INF/resources/images/empty-plant.png"));
        Icon.Options usFlagIconOptions = new Icon.Options();
        usFlagIconOptions.setImg(streamResource);


        Icon usFlagIcon = new Icon(usFlagIconOptions);
        MarkerFeature usOffice = new MarkerFeature(usOfficeCoordinates, usFlagIcon);
        map.getFeatureLayer().addFeature(usOffice);
    }


}
