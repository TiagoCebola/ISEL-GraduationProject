package com.example.application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class Controllers {

    @RequestMapping("/Roboto-Regular.ttf")
    public RedirectView localRedirect() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080");
        return redirectView;
    }


}
