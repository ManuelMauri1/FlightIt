package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class CredentialsValidator implements Validator {
    @Autowired
    private CredentialsRepository credentialsRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Credentials.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotBlank");
        Credentials credentials = (Credentials) target;
        if(credentialsRepository.existsByUsername(credentials.getUsername()))
            errors.reject("credendials.username.duplicati");
    }
}