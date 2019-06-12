package ru.cbrf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Uer {
    private long vKey;
    private String uer;
    private String name;
}
