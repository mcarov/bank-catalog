package ru.cbrf.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import ru.cbrf.domain.Bank;

import java.text.ParseException;
@Data
@NoArgsConstructor
public class BankModel {
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
    private String changingDate;
    private String ksnp;
    private String inclusionDate;
    private String controlDate;

    public BankBuilder getBankBuilder() {
        return new BankBuilder();
    }

    public class BankBuilder {
        private Bank bank;

        private BankBuilder() {
            bank = new Bank();
        }

        public Bank build() {
            bank.setReal(real);
            bank.setPzn(pzn);
            bank.setUer(uer);
            bank.setRgn(rgn);
            bank.setInd(ind);
            bank.setTnp(tnp);
            bank.setNnp(nnp);
            bank.setAdr(adr);
            bank.setRkc(rkc);
            bank.setNameP(nameP);
            bank.setNewNum(newNum);
            bank.setPhoneNum(phoneNum);
            bank.setRegNum(regNum);
            bank.setOkpo(okpo);
            try {
                bank.setChangingDate(DateUtils.parseDate(changingDate, "yyyy-MM-dd"));
            }
            catch(ParseException e) {
                bank.setChangingDate(null);
            }
            bank.setKsnp(ksnp);
            try {
                bank.setInclusionDate(DateUtils.parseDate(inclusionDate, "yyyy-MM-dd"));
            }
            catch(ParseException e) {
                bank.setInclusionDate(null);
            }
            try {
                bank.setControlDate(DateUtils.parseDate(controlDate, "yyyy-MM-dd"));
            }
            catch(ParseException e) {
                bank.setControlDate(null);
            }

            return bank;
        }
    }
}
