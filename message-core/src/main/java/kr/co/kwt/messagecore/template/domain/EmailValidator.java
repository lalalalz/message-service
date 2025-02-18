package kr.co.kwt.messagecore.template.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmailValidator implements Validator {

    private static final String PATTERN = "\"^[a-zA-Z0-9_+&*-]+(?:\\\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,7}$\"";
    private String target;

    @Override
    public boolean validate() {
        if (target == null) {
            throw new ValidationException();
        }

        return PATTERN.matches(target);
    }
}
