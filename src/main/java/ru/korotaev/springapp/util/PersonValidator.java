package ru.korotaev.springapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.korotaev.springapp.dao.PersonDAO;
import ru.korotaev.springapp.models.Person;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personDAO.validateEmail(person.getEmail())!=null && !personDAO.validateEmail(person.getEmail()).equals(person)){
            errors.rejectValue("email", "", "This email is already taken");
        }
    }
}
