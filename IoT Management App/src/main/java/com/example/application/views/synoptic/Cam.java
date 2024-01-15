package com.example.application.views.synoptic;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;


@Tag("webcomponent-api")
@JsModule("cam-component/videoStream.js")
@JsModule("cam-component/webComponentApi.ts")
@JsModule("cam-component/widget-element.ts")
public class Cam extends Component {


    public Cam(int id, String name, String svgPath) {

        getElement()
            .setProperty("id", String.valueOf(id))
            .setProperty("name", name)
            .setProperty("svgPath", svgPath);
    }

}
