package ru.cbrf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Bank {
    private String real;
    private String pzn;
    private String uer;
    private String rgn;
    private String ind;
    private String tnp;
    private String nnp;
    private String adr;
    private String rkc;
    private String nameP;
    private String newNum;
    private String phoneNum;
    private String regNum;
    private String okpo;
    private Date changingDate;
    private String ksnp;
    private Date inclusionDate;
    private Date controlDate;
}
