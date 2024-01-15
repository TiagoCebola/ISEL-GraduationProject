package com.example.application.views.login;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private LoginForm login = new LoginForm();

    public LoginView() {

        // NAVBAR
        HorizontalLayout horizontalLayout = new HorizontalLayout();
            Paragraph companyName = new Paragraph();
            companyName.setText("Company Name");
        horizontalLayout.add(companyName);
        horizontalLayout.addClassName("navbar");

        // MIDDLE
        HorizontalLayout mainBody = new HorizontalLayout();

            // MIDDLE FORM CONF
            VerticalLayout loginLayout = new VerticalLayout();
                LoginI18n i18n = LoginI18n.createDefault();
                i18n.getForm().setTitle("Sign in");

                // Acrescentar texto com descrição para login user/user or admin/admin
                i18n.setAdditionalInformation(null);
                login.getElement().getStyle().set("box-shadow", "0px 0px 20px 4px rgb(0 0 0 / 10%)");
                login.setI18n(i18n);
                login.setAction("login");
            loginLayout.addClassName("loginLayout");
            loginLayout.add(login);

        mainBody.add(loginLayout);
        mainBody.addClassName("mainBody");

        // FOOTER
        HorizontalLayout footer = new HorizontalLayout();

            VerticalLayout footerInfo = new VerticalLayout();
                Paragraph copyright = new Paragraph();
                copyright.setText("Copyright ©2022");
                Paragraph rightsReserved = new Paragraph();
                rightsReserved.setText("Todos os direitos reservados.");
            footerInfo.add(copyright, rightsReserved);

            VerticalLayout footerMedia = new VerticalLayout();
            Div mediaWrapper = new Div();
                Anchor git = new Anchor();
                Anchor whatsapp = new Anchor();
                Anchor facebook = new Anchor();
            mediaWrapper.add(git, whatsapp, facebook);
            footerMedia.add(mediaWrapper);

        footer.add(footerInfo, footerMedia);
        footer.addClassName("footer");


        addClassName("login-view");
        setSizeFull();


        add(horizontalLayout, mainBody, footer);

    }

        @Override
        public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
            if(beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
                login.setError(true);
            }
        }
}
