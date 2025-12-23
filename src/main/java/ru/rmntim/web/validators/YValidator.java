package ru.rmntim.web.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("yValidator")
public class YValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Ошибка валидации", "Y не может быть пустым"));
        }

        double yValue;
        try {
            yValue = Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Ошибка валидации", "Y должен быть числом"));
        }

        // Проверка диапазона -3 до 3
        if (yValue < -3 || yValue > 3) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Ошибка валидации", "Y должен быть от -3 до 3"));
        }

        // Проверка на целое число
        if (Math.abs(yValue - Math.round(yValue)) > 0.0001) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Ошибка валидации", "Y должен быть целым числом"));
        }
    }
}