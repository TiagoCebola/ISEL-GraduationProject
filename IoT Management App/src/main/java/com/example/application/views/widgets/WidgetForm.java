package com.example.application.views.widgets;

import com.example.application.data.entity.SampleWidget2;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WidgetForm extends FormLayout {
    Binder<SampleWidget2> binder = new BeanValidationBinder<SampleWidget2>(SampleWidget2.class);

    Upload image = new Upload();
    Image imagePreview = new Image();
    TextField firstName = new TextField("First Name");
    TextField lastName = new TextField("Last Name");
    TextField email  = new TextField("Email");
    TextField phone = new TextField("Phone");
    DatePicker dateOfBirth = new DatePicker("Date Of Birth");
    Upload status = new Upload();
    Image statusPreview = new Image();

    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete");
    Button save = new Button("Save");

    private SampleWidget2 widget;

    public WidgetForm() {
        addClassName("widget-form");

        binder.bindInstanceFields(this); // Nomes dos fields acima tem de ser igual aos da classe base Widget

        Div wrapperImage = new Div();
        Label imageLabel = new Label("Image");
        imagePreview.setWidth("100%");
        imagePreview.addClassName("imagePreview");
        image.getStyle().set("box-sizing", "border-box");
        image.getElement().appendChild(imagePreview.getElement());

        // Image Upload Functionalities
        attachImageUpload(image, imagePreview);
        attachImageUpload(status, statusPreview);

        wrapperImage.add(imageLabel, imagePreview, image);
        wrapperImage.addClassName("wrapperImage");

        Div wrapperStatus = new Div();
        Label statusLabel = new Label("Status");
        statusPreview.setWidth("100%");
        status.getStyle().set("box-sizing", "border-box");
        status.getElement().appendChild(statusPreview.getElement());

        wrapperStatus.add(statusLabel, statusPreview, status);
        wrapperStatus.addClassName("wrapperStatus");

        // CSS CLASSES FOR INPUT LABELS
        firstName.addClassName("input-form");
        lastName.addClassName("input-form");
        email.addClassName("input-form");
        phone.addClassName("input-form");
        dateOfBirth.addClassName("input-form");

        Component[] fields = new Component[]{wrapperImage, firstName, lastName, email, phone, dateOfBirth, wrapperStatus, createButtonLayout()};
        add(fields);
    }

    // FAZ VALIDAÇÕES PARA ACEITAR IMAGENS
    private void attachImageUpload(Upload upload, Image preview) {
        ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream();
        upload.setAcceptedFileTypes("image/*");
        upload.setReceiver((fileName, mimeType) -> {
            return uploadBuffer;
        });
        upload.addSucceededListener(e -> {
            String mimeType = e.getMIMEType();
            String base64ImageData = Base64.getEncoder().encodeToString(uploadBuffer.toByteArray());
            String dataUrl = "data:" + mimeType + ";base64,"
                    + UriUtils.encodeQuery(base64ImageData, StandardCharsets.UTF_8);
            upload.getElement().setPropertyJson("files", Json.createArray());
            preview.setSrc(dataUrl);
            uploadBuffer.reset();
        });
        preview.setVisible(false);
    }

    public void setWidget(SampleWidget2 widget) {
        this.widget = widget;
        binder.readBean(widget);
    }

    public Component createButtonLayout() {

        // Colors
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        // Validations
        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, widget)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        // ShortCut Keys on PC
        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.DELETE);
        cancel.addClickShortcut(Key.ESCAPE);

        HorizontalLayout buttonsLayout = new HorizontalLayout(save, delete, cancel);
        buttonsLayout.addClassName("form-buttons-layout");

        return buttonsLayout;
    }

    // Validation for SAVE button
    private void validateAndSave() {
        try {
            binder.writeBean(widget);
            fireEvent(new SaveEvent(this, widget));
        } catch (ValidationException e) {
            e.printStackTrace(); // NOTIF AO USER
        }
    }

    private Component createButtonLayout2(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
        return buttonLayout;
    }



    // VALIDATIONS
    public static abstract class WidgetFormEvent extends ComponentEvent<WidgetForm> {

        private SampleWidget2 widget; // Armazenar os dados

        protected WidgetFormEvent(WidgetForm source, SampleWidget2 widget) {
            super(source, false);
            this.widget = widget;
        }

        public SampleWidget2 getWidget() {
            return widget;
        }

    }

    public static class SaveEvent extends WidgetFormEvent {
        SaveEvent(WidgetForm source, SampleWidget2 widget) {
            super(source, widget);
        }
    }

    public static class DeleteEvent extends WidgetFormEvent {
        DeleteEvent(WidgetForm source, SampleWidget2 widget) {
            super(source, widget);
        }
    }

    public static class CloseEvent extends WidgetFormEvent {
        CloseEvent(WidgetForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>>Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }


}
