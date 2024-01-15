package com.example.application.views.userslist;

import com.example.application.Application;
import com.example.application.data.entity.Person;
import com.example.application.data.service.person.PersonRepository;
import com.example.application.data.service.person.PersonService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;


@PageTitle("Users List")
@Route(value = "userList", layout = MainLayout.class)
@RolesAllowed("ADMIN")
public class UsersListView extends VerticalLayout {

    private Grid<Person> grid = new Grid<>(Person.class, false);
    private PersonRepository repo =  new PersonRepository(Application.db);
    private PersonService personService = new PersonService(repo);

    public UsersListView() {
        setSizeFull();

        updateList();

        configureGrid();
        add(getContent());
    }


    private void updateList() {
        grid.setItems(personService.listAllPersons());
    }


    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(1);
        content.addClassNames("content");
        content.setSizeFull();

        return content;
    }

    private void configureGrid() {
        grid.setSizeFull();

        // Perfil image configuration
        LitRenderer<Person> imageRenderer = LitRenderer.<Person>of(
                "<span style='border-radius: 50%; overflow: hidden; display: flex; align-items: center; justify-content: center; width: 64px; height: 64px'><img style='max-width: 100%' src='${item.image}' /></span>")
                .withProperty("image", Person::getImageRenderer);


        grid.addColumn(imageRenderer).setHeader("Image").setWidth("96px").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
        grid.addColumn("username").setAutoWidth(true);
        grid.addColumn("role").setAutoWidth(true);
        grid.addColumn("email").setAutoWidth(true);
        grid.addColumn("dateOfBirth").setAutoWidth(true);

    }

}
