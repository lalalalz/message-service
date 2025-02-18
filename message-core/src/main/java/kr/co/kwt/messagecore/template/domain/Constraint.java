package kr.co.kwt.messagecore.template.domain;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
public class Constraint {

    String variableName;
    ConstraintType constraintType;

    @AllArgsConstructor
    public enum ConstraintType {

        LENGTH(LengthValidator.class),
        NUMBER(NumberValidator.class),
        PHONE(PhoneValidator.class),
        EMAIL(EmailValidator.class),
        ;

        private final Class<? extends Validator> validatorType;
    }
}
