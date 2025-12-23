package ru.rmntim.web.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("inputBean")
@ApplicationScoped
public class InputBean implements Serializable {

    // X: от -2.0 до 1.5 с шагом 0.5
    public List<SelectItem> getXValues() {
        List<SelectItem> items = new ArrayList<>();
        double[] values = {-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5};

        for (double value : values) {
            items.add(new SelectItem(value, String.valueOf(value)));
        }
        return items;
    }

    // R: от 1 до 5 с шагом 1
    public List<SelectItem> getRValues() {
        List<SelectItem> items = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            items.add(new SelectItem((double) i, String.valueOf(i)));
        }
        return items;
    }
}