package com.example.application.data.service.widget_iot;

import com.example.application.data.entity.Widget_IoT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Service
public class Widget_IoTService {

    private final Widget_IoTRepository repository;

    @Autowired
    public Widget_IoTService(Widget_IoTRepository repository) {
        this.repository = repository;
    }

    public Optional<Widget_IoT> getWidget_IoT(String wiotId) {
        return repository.getWidget_IoT(wiotId);
    }

    public Boolean createWidget_IoT(String iotType, String comun_proto, String svgImage) {
        return repository.createWidget_IoT(iotType, comun_proto, svgImage);
    }

    //MAYBE NOT NEEDED
    public Boolean updateWidget_IoT(String wiotId, String iotType, String comun_proto, String svgImage) {
        return repository.updateWidget_IoT(wiotId,iotType, comun_proto, svgImage);
    }

    public void deleteWidget_IoT(String wiotId) {
        repository.deleteWidget_IoT(wiotId);
    }

    public List<Widget_IoT> listAllWidgets() {

        List<Widget_IoT> widgetsList =  repository.getAllWidgets();

        ListIterator<Widget_IoT> widgetListIterator =  widgetsList.listIterator();
        while( widgetListIterator.hasNext() ) {

            Widget_IoT widget  = widgetListIterator.next();
            String imagePath = widget.getSvgImage();

            File folder = new File("src\\main\\resources\\META-INF\\widgets\\" + imagePath);
            widget.setSvgImage("\\widgets\\" + folder.getName());

        }

        return widgetsList;
    }

    public int countWidgets() {
        return repository.getAllWidgets().size();
    }
}
