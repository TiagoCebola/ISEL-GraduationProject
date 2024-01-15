package com.example.application.views.notFound;


import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import javax.servlet.http.HttpServletResponse;

@ParentLayout(MainLayout.class)
public class NotFoundView extends RouteNotFoundError {


        @Override
        public int setErrorParameter(BeforeEnterEvent event,
                                     ErrorParameter<NotFoundException> parameter) {

            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.addClassName("login-view");

            Paragraph navigateMsg = new Paragraph();
            navigateMsg.setText("Could not navigate to '"
                    + event.getLocation().getPath()
                    + "'");
            navigateMsg.getStyle().set("font-size", 2 + "rem");
            navigateMsg.getStyle().set("text-align", "center");

            Image img = new Image("images/404.png", "Not found page");
            verticalLayout.add(navigateMsg, img);

            verticalLayout.setSizeFull();
            verticalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);

            getElement().setChild(0, verticalLayout.getElement());

            getElement().getStyle().set("height", 100 + "%");
            return HttpServletResponse.SC_NOT_FOUND;
        }

}


