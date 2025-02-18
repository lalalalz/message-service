package kr.co.kwt.messagecore.template.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LengthValidator implements Validator {

    private final int minLength;
    private final int maxLength;
    private final String target;


    @Override
    public boolean validate() {
        return target.length() >= minLength && target.length() <= maxLength;
    }
}
