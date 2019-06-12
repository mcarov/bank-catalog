package ru.cbrf.service;

import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.cbrf.domain.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbfReaderService {
    private final File banksFile;
    private final File pznFile;
    private final File tnpFile;
    private final File uerFile;
    private final File regFile;

    public DbfReaderService() throws FileNotFoundException {
        banksFile = ResourceUtils.getFile("classpath:BNKSEEK.DBF");
        pznFile = ResourceUtils.getFile("classpath:PZN.DBF");
        tnpFile = ResourceUtils.getFile("classpath:TNP.DBF");
        uerFile = ResourceUtils.getFile("classpath:UER.DBF");
        regFile = ResourceUtils.getFile("classpath:REG.DBF");
    }

    List<Bank> loadBanksData() throws FileNotFoundException {
        DBFReader reader = new DBFReader(new FileInputStream(banksFile), Charset.forName("Cp866"));

        DBFRow row;
        List<Bank> banks = new ArrayList<>(2109);
        while((row = reader.nextRow()) != null) {
            banks.add(getBank(row));
        }

        return banks;
    }

    List<Pzn> loadPznData() throws FileNotFoundException {
        DBFReader reader = new DBFReader(new FileInputStream(pznFile), Charset.forName("Cp866"));

        DBFRow row;
        List<Pzn> pznDataList = new ArrayList<>();
        while((row = reader.nextRow()) != null) {
            pznDataList.add(getPzn(row));
        }

        return pznDataList;
    }

    List<Tnp> loadTnpData() throws FileNotFoundException {
        DBFReader reader = new DBFReader(new FileInputStream(tnpFile), Charset.forName("Cp866"));

        DBFRow row;
        List<Tnp> tnpDataList = new ArrayList<>();
        while((row = reader.nextRow()) != null) {
            tnpDataList.add(getTnp(row));
        }

        return tnpDataList;
    }

    List<Uer> loadUerData() throws FileNotFoundException {
        DBFReader reader = new DBFReader(new FileInputStream(uerFile), Charset.forName("Cp866"));

        DBFRow row;
        List<Uer> uerDataList = new ArrayList<>();
        while((row = reader.nextRow()) != null) {
            uerDataList.add(getUer(row));
        }

        return uerDataList;
    }

    List<Rgn> loadRgnData() throws FileNotFoundException {
        DBFReader reader = new DBFReader(new FileInputStream(regFile), Charset.forName("Cp866"));

        DBFRow row;
        List<Rgn> rgnDataList = new ArrayList<>();
        while((row = reader.nextRow()) != null) {
            rgnDataList.add(getRgn(row));
        }

        return rgnDataList;
    }

    private Bank getBank(DBFRow row) {
        Bank bank = new Bank();

        bank.setReal(row.getString(1));
        bank.setPzn(row.getString(2));
        bank.setUer(row.getString(3));
        bank.setRgn(row.getString(4));
        bank.setInd(row.getString(5));
        bank.setTnp(row.getString(6));
        bank.setNnp(row.getString(7));
        bank.setAdr(row.getString(8));
        bank.setRkc(row.getString(9));
        bank.setNameP(row.getString(10));
        bank.setNewNum(row.getString(12));
        bank.setPhoneNum(row.getString(18));
        bank.setRegNum(row.getString(19));
        bank.setOkpo(row.getString(20));
        bank.setChangingDate(row.getDate(21));
        bank.setKsnp(row.getString(23));
        bank.setInclusionDate(row.getDate(24));
        bank.setControlDate(row.getDate(25));

        return bank;
    }

    private Pzn getPzn(DBFRow row) {
        Pzn pzn = new Pzn();

        pzn.setVKey(Long.parseLong(row.getString(0)));
        pzn.setPzn(row.getString(1));
        pzn.setImy(row.getString(2));
        pzn.setName(row.getString(3));

        return pzn;
    }

    private Tnp getTnp(DBFRow row) {
        Tnp tnp = new Tnp();

        tnp.setVKey(Long.parseLong(row.getString(0)));
        tnp.setTnp(row.getString(1));
        tnp.setFullName(row.getString(2));
        tnp.setShortName(row.getString(3));

        return tnp;
    }

    private Uer getUer(DBFRow row) {
        Uer uer = new Uer();

        uer.setVKey(Long.parseLong(row.getString(0)));
        uer.setUer(row.getString(1));
        uer.setName(row.getString(2));

        return uer;
    }

    private Rgn getRgn(DBFRow row) {
        Rgn rgn = new Rgn();

        rgn.setVKey(Long.parseLong(row.getString(0)));
        rgn.setRgn(row.getString(1));
        rgn.setName(row.getString(2));
        rgn.setCenter(row.getString(3));
        rgn.setNameT(row.getString(4));

        return rgn;
    }
}
