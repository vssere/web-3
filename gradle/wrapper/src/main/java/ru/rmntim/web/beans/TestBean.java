package ru.rmntim.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

@Named("testBean")
@RequestScoped
public class TestBean implements Serializable {

    private String text;
    private String message;

    public TestBean() {
        System.out.println("[TestBean] Создан");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void submit() {
        System.out.println("[TestBean] submit: " + text);
        message = "Вы ввели: '" + text + "' в " + new Date();
        text = "";
    }

    public String getElTest() {
        return "EL работает! " + new Date();
    }
}