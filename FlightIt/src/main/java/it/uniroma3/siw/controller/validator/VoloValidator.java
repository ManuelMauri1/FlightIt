package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Volo;
import it.uniroma3.siw.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class VoloValidator implements Validator {
    @Autowired
    private VoloRepository voloRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Volo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aereoportoP", "NotBlank");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aereoportoA", "NotBlank");
        Volo volo = (Volo) target;
        if(voloRepository.existsVoloByDataPartenzaAndAereoportoPartenzaAndAereoportoArrivoAndOraPartenza(volo.getDataPartenza(), volo.getAereoportoPartenza(),volo.getAereoportoArrivo(),volo.getOraPartenza()))
            errors.reject("volo.duplicati");
    }
}
