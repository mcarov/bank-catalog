package ru.cbrf.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.cbrf.domain.Pzn;
import ru.cbrf.repository.PznRepository;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PznService {
    private final DbfReaderService readerService;
    private final PznRepository pznRepository;

    @PostConstruct
    public void createPznDataTable() throws FileNotFoundException {
        List<Pzn> pznDataList = readerService.loadPznData();
        pznDataList.forEach(this::savePzn);

        System.out.println("loading pzn data complete");
    }

    public List<Pzn> getAllPzn() {
        return pznRepository.getAllPzn();
    }

    public List<String> searchPznByName(String query) {
        return pznRepository.getAllPzn().stream().
                filter(pzn -> StringUtils.containsIgnoreCase(pzn.getName(), query)).
                map(Pzn::getPzn).collect(Collectors.toList());
    }

    public String getPznNameByCode(String code) {
        return pznRepository.getPznNameByCode(code);
    }

    public void savePzn(Pzn pzn) {
        pznRepository.savePzn(pzn);
    }
}
