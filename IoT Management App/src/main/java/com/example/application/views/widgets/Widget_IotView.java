package com.example.application.views.widgets;

import com.example.application.Application;
import com.example.application.data.entity.Widget_IoT;
import com.example.application.data.service.widget_iot.Widget_IoTRepository;
import com.example.application.data.service.widget_iot.Widget_IoTService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PageTitle("Widgets List")
@Route(value = "widgetsList", layout = MainLayout.class)
@PermitAll
public class Widget_IotView extends VerticalLayout {

    private Grid<Widget_IoT> grid = new Grid<>(Widget_IoT.class, false);

    private Widget_IoTRepository repo =  new Widget_IoTRepository(Application.db);
    private Widget_IoTService service = new Widget_IoTService(repo);


    public Widget_IotView() {
        addClassName("widget-view");
        setSizeFull();

        configureGrid();
        add(getContent());

        updateList();
    }


    private void updateList() {
        grid.setItems(service.listAllWidgets());
    }

    private Component getContent() {

        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(1, grid);

        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }


    private void configureGrid() {
        grid.setSizeFull();

        // Image configuration
        LitRenderer<Widget_IoT> imageRenderer = LitRenderer.<Widget_IoT>of(
                "<span style='border-radius: 50%; overflow: hidden; display: flex; align-items: center; justify-content: center; width: 64px; height: 64px'><img style='max-width: 100%' src=${item.svgImage} /></span>")
                .withProperty("svgImage", Widget_IoT::getSvgImage);
        grid.addColumn(imageRenderer).setHeader("Image").setWidth("96px").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);

        grid.addColumn("wiotId").setHeader("Id").setAutoWidth(true);
        grid.addColumn("iotType").setHeader("Type").setAutoWidth(true);
        grid.addColumn("comun_proto").setHeader("Protocol").setAutoWidth(true);
    }

}
