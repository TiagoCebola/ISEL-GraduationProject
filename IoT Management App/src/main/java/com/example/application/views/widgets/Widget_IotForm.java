package com.example.application.views.widgets;

import com.example.application.data.entity.Widget_IoT;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import elemental.json.Json;
import org.springframework.web.util.UriUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Widget_IotForm extends FormLayout {

    // Groups all the info
    Binder<Widget_IoT> binder = new BeanValidationBinder<>(Widget_IoT.class);

    // Image conf
    Upload svgImage = new Upload();
    Image svgImagePreview = new Image();

    // Global file img path ****
    private String filePath = "";

    // Manipulate image path on widgetRegisterView
    public Image getSvgImagePreview() {
        return svgImagePreview;
    }
    public void setSvgImagePreview(Image svgImagePreview) {
        this.svgImagePreview = svgImagePreview;
    }

    // Widgets Info
    TextField wiotId = new TextField("Widget ID");
    TextField iotType = new TextField("Type");
    TextField comun_proto  = new TextField("Protocol");

    // Buttons
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete");
    private Button save = new Button("Save");
    private Button update = new Button("Update");

    // Widget Instance
    Widget_IoT widget;

    public Widget_IotForm() {
        addClassName("widgetRegisterForm-view");

        binder.bindInstanceFields(this); // Nomes dos fields acima tem de ser igual aos da classe base Widget

        // Manipulate image display
        Div wrapperImage = new Div();
        Label imageLabel = new Label("Image");
        imageLabel.setClassName("imageLabel");
        svgImagePreview.setWidth("100%");
        svgImagePreview.addClassName("imagePreview");
        svgImagePreview.setVisible(true);
        svgImagePreview.setSrc("widgets/widgetAdd.svg");
        svgImage.getStyle().set("box-sizing", "border-box");
        svgImage.getElement().appendChild(svgImagePreview.getElement());

        // Image Upload Functionalities
        attachImageUpload(svgImage, svgImagePreview);

        // Adding to wrapper all image related
        wrapperImage.add(imageLabel, svgImagePreview, svgImage);
        wrapperImage.addClassName("wrapperImage");


        // CSS CLASSES FOR INPUT LABELS
        wiotId.addClassName("input-form");
        wiotId.setPlaceholder("Generated automatically by the database if your creating a widget");

        iotType.addClassName("input-form");
        iotType.setRequired(true);
        iotType.setPlaceholder("Camera, Recorder, Lamp");

        comun_proto.addClassName("input-form");
        comun_proto.setRequired(true);
        comun_proto.setPlaceholder("Onvif, Rest");

        // Add to fields all components
        Component[] fields = new Component[]{wiotId, iotType, comun_proto, wrapperImage, createButtonLayout()};
        add(fields);
    }


    // Image Upload validations
    private void attachImageUpload(Upload upload, Image preview) {
        ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream();
        upload.setAcceptedFileTypes("image/*");

        upload.setReceiver((fileName, mimeType) -> {
            return uploadBuffer;
        });

        upload.addSucceededListener(e -> {

            // Get information about the uploaded file
            String fileName = e.getFileName();
            File f = new File("src\\main\\resources\\META-INF\\resources\\widgets\\" + fileName);
            this.filePath = "\\widgets\\" + fileName;

            // Writing file
            try {
                FileOutputStream fos1 = new FileOutputStream(f);
                fos1.write(uploadBuffer.toByteArray(), 0, uploadBuffer.size());
                fos1.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            }

            // Necessary to display image
            String mimeType = e.getMIMEType();
            String base64ImageData = Base64.getEncoder().encodeToString(uploadBuffer.toByteArray());
            String dataUrl = "data:" + mimeType + ";base64," + UriUtils.encodeQuery(base64ImageData, StandardCharsets.UTF_8);

            preview.setSrc(dataUrl);
            upload.getElement().setPropertyJson("files", Json.createArray());
            uploadBuffer.reset();
        });

        preview.setVisible(true);
    }

    // OK
    public void setWidget(Widget_IoT widget) {
        this.widget = widget;
        binder.readBean(widget);
    }

    // Buttons display and functionalities
    public Component createButtonLayout() {

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");

        // Colors
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        // Validations
        save.addClickListener(event -> validateAndFireEvent( "SaveEvent"));
        update.addClickListener(event -> validateAndFireEvent( "EditEvent"));
        delete.addClickListener(event -> validateAndFireEvent("DeleteEvent"));
        cancel.addClickListener(event -> {
            svgImagePreview.setSrc("widgets/widgetAdd.svg");
            fireEvent(new CloseEvent(this));
        });

        // ShortCut Keys on PC
        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.DELETE);
        cancel.addClickShortcut(Key.ESCAPE);

        buttonLayout.add(save);
        buttonLayout.add(update);
        buttonLayout.add(delete);
        buttonLayout.add(cancel);

        return buttonLayout;
    }

    // Trigger the event to upper class
    private void validateAndFireEvent(String eventType) {
        try {
            editWidget();
            binder.writeBean(widget);

            if(eventType.equalsIgnoreCase("SaveEvent")) {
                fireEvent(new Widget_IotForm.SaveEvent(this, widget));
            }
            else if(eventType.equalsIgnoreCase("DeleteEvent")) {
                fireEvent(new Widget_IotForm.DeleteEvent(this, widget));
            }

            else if(eventType.equalsIgnoreCase("EditEvent")) {
                fireEvent(new Widget_IotForm.EditEvent(this, widget));
            }

        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Populating form
    private void editWidget() {
        // Updates the form info
        Widget_IoT widget = new Widget_IoT();
        svgImagePreview.setVisible(widget != null);
        svgImagePreview.setSrc(this.filePath);
        widget.setSvgImage(svgImagePreview.getSrc());

        widget.setComun_proto(comun_proto.getValue());
        widget.setIotType(iotType.getValue());
        widget.setWiotId(wiotId.getValue());

        setWidget(widget);
        setVisible(true);
        addClassName("editing");
    }


    // VALIDATIONS
    public static abstract class WidgetFormEvent extends ComponentEvent<Widget_IotForm> {

        private Widget_IoT widget; // Armazenar os dados

        protected WidgetFormEvent(Widget_IotForm source, Widget_IoT widget) {
            super(source, false);
            this.widget = widget;
        }

        public Widget_IoT getWidget() {
            return widget;
        }

    }

    public static class SaveEvent extends Widget_IotForm.WidgetFormEvent {
        SaveEvent(Widget_IotForm source, Widget_IoT widget) {
            super(source, widget);
        }
    }

    public static class EditEvent extends Widget_IotForm.WidgetFormEvent {
        EditEvent(Widget_IotForm source, Widget_IoT widget) {
            super(source, widget);
        }
    }

    public static class DeleteEvent extends Widget_IotForm.WidgetFormEvent {
        DeleteEvent(Widget_IotForm source, Widget_IoT widget) {
            super(source, widget);
        }
    }

    public static class CloseEvent extends Widget_IotForm.WidgetFormEvent {
        CloseEvent(Widget_IotForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
