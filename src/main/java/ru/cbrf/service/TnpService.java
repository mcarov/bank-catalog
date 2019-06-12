package ru.cbrf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cbrf.domain.Tnp;
import ru.cbrf.repository.TnpRepository;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TnpService {
    private final DbfReaderService readerService;
    private final TnpRepository tnpRepository;

    @PostConstruct
    public void createTnpDataTable() throws FileNotFoundException {
        List<Tnp> tnpDataList = readerService.loadTnpData();
        tnpDataList.forEach(this::saveTnp);

        System.out.println("loading tnp data complete");
    }

    public List<Tnp> getAllTnp() {
        return tnpRepository.getAllTnp();
    }

    public String getTnpNameByCode(String code) {
        return tnpRepository.getTnpNameByCode(code);
    }

    public void saveTnp(Tnp tnp) {
        tnpRepository.saveTnp(tnp);
    }
}
