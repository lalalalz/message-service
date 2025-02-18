package kr.co.kwt.messagecore.message.domain;

import lombok.Value;

@Value
public class From {

    public static From SYSTEM = new From("kwt@kwt.co.kr");

    String identifier;
}
