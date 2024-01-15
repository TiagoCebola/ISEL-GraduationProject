package com.example.application.views.home;

import com.example.application.data.entity.Person;
import com.example.application.security.AuthenticatedPerson;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import javax.annotation.security.PermitAll;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

@PageTitle("Home")
@Route(value = "home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {

    public HomeView(AuthenticatedPerson authenticatedPerson) {
        setSpacing(false);

        // Div to wrap the Greetings
        Div welcome = new Div();
        welcome.addClassName("welcome");

            // Get AuthenticatedUser
            Optional<Person> maybeUser = authenticatedPerson.get();
            Person person = null;

            if (maybeUser.isPresent()) {
                person = maybeUser.get();
            }

            VerticalLayout wrapper = new VerticalLayout(); // Align items side by side
            H2 greetingsUser = new H2("Hello, " + person.getUsername() + "!");
            Paragraph phrase = new Paragraph("Welcome back to your personal IoT manager");
            wrapper.add(greetingsUser, phrase);
            wrapper.addClassName("wrapper");

        welcome.add(wrapper);

        // Div to wrap the dates
        Div dateContainer = new Div();
        dateContainer.addClassName("dateContainer");

            Paragraph dateTitle = new Paragraph("Days of the week");

            // Formats the date
            Calendar cal = Calendar.getInstance();


            // Div that holds all the days of the week
            Div allDaysOfTheWeek = new Div();
            allDaysOfTheWeek.addClassName("daysOfWeek");


            // Getting all the days of the week
            for(int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
                Div dayTable = new Div(); // For each day a separate div is created
                dayTable.addClassName("daytable");

                Div weekOfTheDay = new Div();
                addClassName("weekOfTheDay");

                Span numberOfTheDay = new Span();
                addClassName("numberOfTheDay");

                cal.set(Calendar.DAY_OF_WEEK, i);
                weekOfTheDay.add(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.UK));
                numberOfTheDay.add(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));

                dayTable.add(weekOfTheDay, numberOfTheDay);
                allDaysOfTheWeek.add(dayTable);
            }

        dateContainer.add(dateTitle, allDaysOfTheWeek);


        Div imgWrapper = new Div();
            imgWrapper.addClassName("imgWrapper");
            Image img = new Image("images/joy.png", "Greetings");
        imgWrapper.add(img);

        // Adding components to vertical layout
        add(welcome, dateContainer, imgWrapper);

        // Default layout styles
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }


}
