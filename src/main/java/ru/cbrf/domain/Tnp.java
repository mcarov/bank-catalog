package ru.cbrf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Tnp {
    private long vKey;
    private String tnp;
    private String fullName;
    private String shortName;
}
