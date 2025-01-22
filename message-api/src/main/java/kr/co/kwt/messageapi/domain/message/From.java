package kr.co.kwt.messageapi.domain.message;

import lombok.Value;

@Value
public class From {

    public static From SYSTEM = new From("kwt@kwt.co.kr");

    String identifier;
}
