package ru.cbrf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Pzn {
    private long vKey;
    private String pzn;
    private String imy;
    private String name;
}
