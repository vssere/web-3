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
        System.out.println("[YValidator] Проверка значения: " + value);

        if (value == null || value.toString().trim().isEmpty()) {
            throw new ValidatorException(
                    new FacesMessage("Y не может быть пустым"));
        }

        try {
            double yValue = Double.parseDouble(value.toString().trim());

            if (yValue < -3 || yValue > 3) {
                throw new ValidatorException(
                        new FacesMessage("Y должен быть от -3 до 3"));
            }

            // Проверка на целое число
            if (yValue != Math.floor(yValue)) {
                throw new ValidatorException(
                        new FacesMessage("Y должен быть целым числом"));
            }

        } catch (NumberFormatException e) {
            throw new ValidatorException(
                    new FacesMessage("Y должен быть числом"));
        }
    }
}