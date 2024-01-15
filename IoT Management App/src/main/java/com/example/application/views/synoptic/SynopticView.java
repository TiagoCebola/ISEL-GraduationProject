package com.example.application.views.synoptic;

import com.example.application.Application;
import com.example.application.data.entity.SynopticWidget;
import com.example.application.data.entity.Widget_IoT;
import com.example.application.data.service.sinoticWidget.SynopticWidgetRepository;
import com.example.application.data.service.sinoticWidget.SynopticWidgetService;
import com.example.application.data.service.widget_iot.Widget_IoTRepository;
import com.example.application.data.service.widget_iot.Widget_IoTService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.security.PermitAll;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@PageTitle("Synoptic")
@Route(value = "synoptic", layout = MainLayout.class)
@PermitAll
public class SynopticView extends VerticalLayout {

    private Widget_IoTRepository widgetRepo =  new Widget_IoTRepository(Application.db);
    private Widget_IoTService widgetService = new Widget_IoTService(widgetRepo);

    private SynopticWidgetRepository synopticWidgetRepo =  new SynopticWidgetRepository(Application.db);
    private SynopticWidgetService synopticWidgetService = new SynopticWidgetService(synopticWidgetRepo);

    private TextField name;
    private TextField wiotId;
    private Button submit;
    private Button delete;

    public SynopticView()  {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username =  ((UserDetails)principal).getUsername();

        addClassName("synoptic-view");

        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("layout-border");

        H3 smallScreenWarning = new H3("Device size doesn't support Canvas Functionalities, try opening in Full Screen");
        smallScreenWarning.addClassName("smallScreenWarning");
        smallScreenWarning.getStyle().set("display", "none");

        Div canvasWrapper = new Div();
        canvasWrapper.addClassName("canvasWrapper");

        // Creates Web Canvas
        Div webCanvas = new Div();
        webCanvas.addClassName("webCanvas");

        List<SynopticWidget> synopticWidgetList = synopticWidgetService.listAllSynopticWidgets(username);

        int[] synopticWidgetCounter = {0};

        name = new TextField("Your IoT device name");
        wiotId = new TextField("Your widget ID");

        submit = new Button("Submit");
        submit.addClickListener(e -> {

            try {

                Boolean createdSynoptic = synopticWidgetService.createSynopticWidget(name.getValue(),  username, wiotId.getValue());

                if(createdSynoptic) {

                    // TITLE
                    Div box1 = new Div();
                    box1.setText(name.getValue());


                    Optional<Widget_IoT> widget_ioT = widgetService.getWidget_IoT(wiotId.getValue());
                    Optional<SynopticWidget> synopticWidget = synopticWidgetService.getSynopticWidget(name.getValue(), username);

                    // BODY
                    Div box2 = new Div();
                    box2.add(new Cam(synopticWidget.get().getRestID(), name.getValue(), widget_ioT.get().getSvgImage()));

                    // CONTAINER
                    Div boxWrapper = createDivWrapper(box1, box2, webCanvas, synopticWidgetCounter);

                    canvasWrapper.add(boxWrapper);
                    synopticWidgetCounter[0]++;


                    Notification.show("DONE " + name.getValue());
                }

            }
            catch( Exception exception ) {
                Notification.show("NOT SUBMITTED " + name.getValue());
            }

        });

        delete = new Button("Delete");
        delete.addClickListener(e -> {

            Iterator iterator = canvasWrapper.getChildren().iterator();

            while(iterator.hasNext()) {

                Iterator boxWrapperIterator = canvasWrapper.getChildren().iterator();

                while( boxWrapperIterator.hasNext() ) {

                    Div boxWrapper = (Div) iterator.next();

                    if( !boxWrapper.getClassName().equalsIgnoreCase("webCanvas") ) {

                        if ( boxWrapper.getElement().getChild(0).getText().equalsIgnoreCase(name.getValue()) ) {
                            try {
                                Boolean deletedSynoptic = synopticWidgetService.deleteSynopticWidget(name.getValue(),  username, wiotId.getValue());

                                if(deletedSynoptic) {
                                    canvasWrapper.remove(boxWrapper);
                                    Notification.show(name.getValue() + " removed");
                                }
                            }
                            catch( Exception exception ) {
                                Notification.show("NOT DELETED " + name.getValue());
                            }
                        }

                    }


                }

            }

        });


        // POPULATE CANVAS
        ListIterator<SynopticWidget> synopticWidgetListIterator = synopticWidgetList.listIterator();
        while( synopticWidgetListIterator.hasNext() ) {

            SynopticWidget synopticWidget = synopticWidgetListIterator.next();
            Optional<Widget_IoT> widget_ioT = widgetService.getWidget_IoT(synopticWidget.getWiotId());

            // TITLE
            Div box1 = new Div();
            box1.setText(synopticWidget.getName());

            // BODY
            Div box2 = new Div();
            box2.add(new Cam(synopticWidget.getRestID(), synopticWidget.getName(), widget_ioT.get().getSvgImage()) );

            // CONTAINER
            Div boxWrapper = createDivWrapper(box1, box2, webCanvas, synopticWidgetCounter);

            canvasWrapper.add(boxWrapper);
            synopticWidgetCounter[0]++;
        }

        canvasWrapper.add(webCanvas);
        layout.add(canvasWrapper);

        Div shadedWarning = new Div();
        shadedWarning.setText("The shaded part is your canvas where you can click and change your IoT spot");
        shadedWarning.addClassName("shadedWarning");
        layout.add(shadedWarning);


        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");

        buttonLayout.add(name);
        buttonLayout.add(wiotId);
        buttonLayout.add(submit);
        buttonLayout.add(delete);

        submit.getStyle().set("margin-top", "2.45rem");
        delete.getStyle().set("margin-top", "2.45rem");

        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        buttonLayout.setAlignItems(Alignment.CENTER);

        add(layout);
        add(smallScreenWarning);
        add(buttonLayout);

        layout.setSizeFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        layout.getStyle().set("text-align", "center");
    }


    public Div createDivWrapper(Div box1, Div box2, Div webCanvas, int[] synopticWidgetCounter) {

        // Engloba as duas divs
        Div boxWrapper = new Div();
        boxWrapper.addClassName("box-wrapper");
        boxWrapper.add(box1, box2);
        boxWrapper.getStyle().set("position", "absolute");


        boxWrapper.addClickListener( click -> {

            webCanvas.addClickListener(mouse ->  {

                boxWrapper.getStyle().set("top", mouse.getClientY() + "px");
                boxWrapper.getStyle().set("left", mouse.getClientX() + "px");

                mouse.unregisterListener();
            });

            webCanvas.addClickListener(mouse ->  {
                mouse.unregisterListener();
            });

        });


        if(synopticWidgetCounter[0] == 0) {
            boxWrapper.getStyle().set("top", 85 + "px");
            boxWrapper.getStyle().set("left", 550 + "px");
        }


        else if (synopticWidgetCounter[0] == 2) {
            boxWrapper.getStyle().set("top", 85 + "px");
            boxWrapper.getStyle().set("right", 220 + "px");
        }

        else if (synopticWidgetCounter[0] > 2) {
            boxWrapper.getStyle().set("top", (synopticWidgetCounter[0] * 250 / 3) + "px");
            boxWrapper.getStyle().set("right", (synopticWidgetCounter[0] * 220) + "px");
        }

        else {
            boxWrapper.getStyle().set("top", (synopticWidgetCounter[0] * 250) + "px");
            boxWrapper.getStyle().set("left", 550 + "px");
        }


        return boxWrapper;
    }

}