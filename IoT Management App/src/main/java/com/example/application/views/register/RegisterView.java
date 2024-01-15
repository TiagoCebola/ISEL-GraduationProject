package com.example.application.views.register;

import com.example.application.Application;
import com.example.application.data.entity.Person;
import com.example.application.data.service.person.PersonRepository;
import com.example.application.data.service.person.PersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import elemental.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriUtils;

import javax.annotation.security.RolesAllowed;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@PageTitle("Register")
@Route(value = "register", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class RegisterView extends Div {

    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");

    private TextField role = new TextField("Role");
    private DatePicker dateOfBirth = new DatePicker("Birthday");
    private EmailField email = new EmailField("Email address");

    Upload image = new Upload();
    Image imagePreview = new Image();

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button update = new Button("Update");

    private Binder<Person> binder = new Binder(Person.class);

    private PersonRepository repo =  new PersonRepository(Application.db);
    private PersonService personService = new PersonService(repo);

    private String filePath;

    @Autowired
    public RegisterView() {

        addClassName("register-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        clearForm();

        // OK
        cancel.addClickListener(e -> clearForm());
        // OK
        save.addClickListener(e -> {

            Person newPerson = binder.getBean();
            if ( newPerson.getUsername().isEmpty() || newPerson.getPassword().isEmpty() || newPerson.getRole().isEmpty() ||
                    newPerson.getEmail().isEmpty() ) {
                Notification.show("Fill up all the fields");
            }
            else if( newPerson.getPassword().length() < 6) Notification.show("Password less than 6 characters");
            else if (newPerson.getDateOfBirth() == null) Notification.show("Wrong date format: try DD/MM/YYYY");
            else if(!newPerson.getRole().equalsIgnoreCase("ADMIN") &&  !newPerson.getRole().equalsIgnoreCase("USER") )  Notification.show("Role field allows only: Admin or User");

            else {
                Date parsedDate = formatDate(newPerson);

                Optional<Person> userExists = checkIfPersonExists(newPerson);
                if(userExists.isEmpty()) {

                    String imageFile = (filePath == null) ? "users/no-profile-picture.svg" : filePath;
                    Boolean isCreated = personService.createPerson(newPerson.getUsername(), newPerson.getPassword(), newPerson.getRole(), newPerson.getEmail(), parsedDate, imageFile);

                    if(isCreated) Notification.show(binder.getBean().getUsername() + " created with success.");
                    else Notification.show("Update failed, try changing your values!");
                }

                else Notification.show("User already exists, try changing your name!");
                clearForm();
            }

        });
        // OK
        delete.addClickListener(e -> {

            Person newPerson = binder.getBean();
            if ( newPerson.getUsername().isEmpty() ) {
                Notification.show("Fill up the username field");
            }
            else {
                Optional<Person> userExists = checkIfPersonExists(newPerson);

                if( userExists.isPresent() ) {
                    Boolean isDeleted = personService.deletePerson(newPerson.getUsername());
                    if(isDeleted) Notification.show(newPerson.getUsername() + " deleted with success.");
                    else Notification.show("Deleted failed, try changing your values!");
                }

                else Notification.show("User doesn't exist in your db, try changing your name!");

                clearForm();
            }
        });
        // OK
        update.addClickListener(e -> {

            Person newPerson = binder.getBean();
            if ( newPerson.getUsername().isEmpty() || newPerson.getPassword().isEmpty() || newPerson.getRole().isEmpty() ||
                    newPerson.getEmail().isEmpty() ) {
                Notification.show("Fill up all the fields");
            }
            else if( newPerson.getPassword().length() < 6) Notification.show("Password less than 6 characters");
            else if (newPerson.getDateOfBirth() == null) Notification.show("Wrong date format: try DD/MM/YYYY");
            else if(!newPerson.getRole().equalsIgnoreCase("ADMIN") &&  !newPerson.getRole().equalsIgnoreCase("USER") )  Notification.show("Role field allows only: Admin or User");

            else {
                Date parsedDate = null;
                if(newPerson.getDateOfBirth() != null)  parsedDate = formatDate(newPerson);

                Optional<Person> userExists = checkIfPersonExists(newPerson);

                if( userExists.isPresent() ) {

                    String imageFile = (filePath == null) ? "users/no-profile-picture.svg" : filePath;
                    Boolean isUpdated = personService.updatePerson(newPerson.getUsername(), newPerson.getPassword(), newPerson.getRole(), newPerson.getEmail(), parsedDate, imageFile);

                    if(isUpdated) Notification.show(binder.getBean().getUsername() + " updated with success.");
                    else Notification.show("Update failed, try changing your values!");
                }

                else Notification.show("User doesn't exist!");

                clearForm();
            }
        });
    }

    // OK
    private void clearForm() {
        imagePreview.setSrc("svgs/create-user.svg");
        binder.setBean(new Person());
    }

    // OK
    private Component createTitle() {
        return new H3("Personal information");
    }

    // OK
    private Component createFormLayout() {

        FormLayout formLayout = new FormLayout();

        username.setPlaceholder("John Dane");
        username.setRequired(true);

        password.setPlaceholder("Must have at least 6 characters");
        password.setRequired(true);

        email.getElement().setAttribute("name", "email");
        email.setPlaceholder("username@example.com");
        email.setErrorMessage("Please enter a valid example.com email address");
        email.setClearButtonVisible(true);
        email.setPattern("^.+@example\\.com$");
        email.setRequiredIndicatorVisible(true);

        dateOfBirth.setPlaceholder("DD/MM/YYYY");
        dateOfBirth.setRequired(true);

        role.setWidth("120px");
        role.setPlaceholder("Admin or User");
        role.setPreventInvalidInput(true);
        role.setRequired(true);


        // Image config
        Div wrapperImage = new Div();
        Label imageLabel = new Label("Image");
        imageLabel.setClassName("imageLabel");
        imagePreview.setWidth("100%");
        imagePreview.addClassName("imagePreview");
        imagePreview.setVisible(true);
        imagePreview.setSrc("svgs/create-user.svg");
        image.getStyle().set("box-sizing", "border-box");
        image.getElement().appendChild(imagePreview.getElement());

        wrapperImage.add(imageLabel, imagePreview, image);
        wrapperImage.addClassName("wrapperImage");

        // Image Upload Functionalities
        attachImageUpload(image, imagePreview);

        formLayout.add(username, password, dateOfBirth, email, role, wrapperImage);
        return formLayout;
    }

    // OK
    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        update.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        buttonLayout.add(save);
        buttonLayout.add(update);
        buttonLayout.add(delete);
        buttonLayout.add(cancel);

        return buttonLayout;
    }

    // FAZ VALIDAÇÕES PARA ACEITAR IMAGENS
    private void attachImageUpload(Upload upload, Image preview) {
        ByteArrayOutputStream uploadBuffer = new ByteArrayOutputStream();
        upload.setAcceptedFileTypes("image/*");

        upload.setReceiver((fileName, mimeType) -> uploadBuffer);

        upload.addSucceededListener(e -> {

            // Get information about the uploaded file
            String fileName = e.getFileName();
            File f = new File("src\\main\\resources\\META-INF\\resources\\users\\" + fileName);
            this.filePath = "\\users\\" + fileName;

            try {
                FileOutputStream fos1 = new FileOutputStream(f);
                fos1.write(uploadBuffer.toByteArray(), 0, uploadBuffer.size());
                fos1.close();
            } catch (Exception exc) {
                exc.printStackTrace();
            }

            String mimeType = e.getMIMEType();
            String base64ImageData = Base64.getEncoder().encodeToString(uploadBuffer.toByteArray());
            String dataUrl = "data:" + mimeType + ";base64," + UriUtils.encodeQuery(base64ImageData, StandardCharsets.UTF_8);
            preview.setSrc(dataUrl);


            upload.getElement().setPropertyJson("files", Json.createArray());
            uploadBuffer.reset();
        });

        preview.setVisible(true);
    }

    public Optional<Person> checkIfPersonExists(Person person) {
        return personService.getPerson(person.getUsername());
    }

    // PASSAR COMO ARG SOMENTE O LOCAL DATE *****
    public Date formatDate(Person person) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        LocalDate localDate = person.getDateOfBirth();

        String day, month;
        if(localDate.getDayOfMonth() < 10) day = "0" + localDate.getDayOfMonth();
        else day = String.valueOf(localDate.getDayOfMonth());

        if(localDate.getMonthValue() < 10) month = "0" + localDate.getMonthValue();
        else month = String.valueOf(localDate.getMonthValue());

        String dt = day + "-" + month + "-" + localDate.getYear();

        try {
            return format.parse(dt);
        } catch (ParseException parseException) {
            parseException.printStackTrace();
        }

        return null;
    }

}
