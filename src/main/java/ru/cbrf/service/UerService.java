package ru.cbrf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cbrf.domain.Uer;
import ru.cbrf.repository.UerRepository;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UerService {
    private final DbfReaderService readerService;
    private final UerRepository uerRepository;

    @PostConstruct
    public void createUerDataTable() throws FileNotFoundException {
        List<Uer> uerDataList = readerService.loadUerData();
        uerDataList.forEach(this::saveUer);

        System.out.println("loading uer data complete");
    }

    public List<Uer> getAllUer() {
        return uerRepository.getAllUer();
    }

    public String getUerNameByCode(String code) {
        return uerRepository.getUerNameByCode(code);
    }

    public void saveUer(Uer uer) {
        uerRepository.saveUer(uer);
    }
}
