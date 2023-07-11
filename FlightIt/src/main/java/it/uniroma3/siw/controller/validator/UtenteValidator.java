package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class UtenteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Utente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "NotBlank");
        Utente utente = (Utente) target;
        if(utente.getDataNascita().isAfter(LocalDate.now()))
            errors.reject("PastOrPresent.dataNascita");
    }
}
