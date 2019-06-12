package ru.cbrf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Rgn {
    private long vKey;
    private String rgn;
    private String name;
    private String center;
    private String nameT;
}
