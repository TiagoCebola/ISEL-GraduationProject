package com.example.application.views.register;

import com.example.application.Application;
import com.example.application.data.entity.Widget_IoT;
import com.example.application.data.service.widget_iot.Widget_IoTRepository;
import com.example.application.data.service.widget_iot.Widget_IoTService;
import com.example.application.views.MainLayout;
import com.example.application.views.widgets.Widget_IotForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.jdbi.v3.core.transaction.TransactionException;

import javax.annotation.security.RolesAllowed;


@PageTitle("Widgets Register")
@Route(value = "widgetsRegister/", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class WidgetRegisterView extends VerticalLayout {

    private Widget_IoTRepository repo =  new Widget_IoTRepository(Application.db);
    private Widget_IoTService service = new Widget_IoTService(repo);

    Widget_IotForm form;

    public WidgetRegisterView() {
        //addClassName("widget-view2");
        setSizeFull();

        add(createTitle());

        configureForm();
        add(getContent());
    }

    // OK
    private Component createTitle() {
        return new H3("Widget information");
    }


    // OK
    private Component getContent() {

        HorizontalLayout content = new HorizontalLayout(form);
        content.setFlexGrow(1, form);

        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }

    // OK
    private void configureForm() {
        form = new Widget_IotForm();
        form.setWidth("20em");

        form.addListener(Widget_IotForm.SaveEvent.class, this::saveWidget);
        form.addListener(Widget_IotForm.DeleteEvent.class, this::deleteWidget);
        form.addListener(Widget_IotForm.EditEvent.class, this::editWidget);
        form.addListener(Widget_IotForm.CloseEvent.class, e -> clearForm());
    }

    // Erases form inputs
    private void clearForm() {
        form.setWidget(new Widget_IoT());
    }


    private void editWidget(Widget_IotForm.EditEvent event) {

        // All the space for the Widget
        if(event.getWidget() == null) {
            form.setWidget(event.getWidget());
        }

        // Updates the form info
        else {
            form.setWidget(event.getWidget());
            form.setVisible(true);
            addClassName("editing");
        }

        Widget_IoT widget = event.getWidget();

        if ( widget.getComun_proto().isEmpty() || widget.getIotType().isEmpty() || widget.getWiotId().isEmpty() || widget.getSvgImage().isEmpty()) {
            Notification.show("Fill up the remaining fields");
        }

        else {
            try {
                service.updateWidget_IoT(widget.getWiotId(), widget.getIotType(), widget.getComun_proto(), widget.getSvgImage());
                Notification.show("Widget updated with success");
                clearForm();

            } catch (Exception e) {
                Notification.show("Wrong widget id");
                e.printStackTrace();
            }

        }

    }

    private void saveWidget(Widget_IotForm.SaveEvent event) {

        // All the space for the Widget
        if(event.getWidget() == null) {
            form.setWidget(event.getWidget());
        }

        // Updates the form info
        else {
            form.setWidget(event.getWidget());
            form.setVisible(true);
            addClassName("editing");
        }

        Widget_IoT widget = event.getWidget();
        if ( widget.getComun_proto().isEmpty() || widget.getIotType().isEmpty() || widget.getSvgImage().isEmpty() ) {
            Notification.show("Fill up the required fields");
        }

        else {
            service.createWidget_IoT(widget.getIotType(), widget.getComun_proto(), widget.getSvgImage());
            Notification.show("Widget added with success");
            clearForm();
        }

    }

    private void deleteWidget(Widget_IotForm.DeleteEvent event) {

        // All the space for the Widget
        if(event.getWidget() == null) {
            form.setWidget(event.getWidget());
        }

        // Updates the form info
        else {
            form.setWidget(event.getWidget());
            form.setVisible(true);
            addClassName("editing");
        }

        if ( event.getWidget().getWiotId().isEmpty() ) {
            Notification.show("Insert the id of the widget you want to delete");
        }

        else {
            try {
                service.deleteWidget_IoT(event.getWidget().getWiotId());
                Notification.show("Widget deleted with success");
            }
            catch (TransactionException exp) {
                Notification.show("Widget is being used by an user");
            }

        }

        clearForm();
    }

}
