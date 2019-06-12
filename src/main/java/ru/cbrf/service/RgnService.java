package ru.cbrf.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.cbrf.domain.Rgn;
import ru.cbrf.repository.RgnRepository;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RgnService {
    private final DbfReaderService readerService;
    private final RgnRepository rgnRepository;

    @PostConstruct
    public void createRgnDataTable() throws FileNotFoundException {
        List<Rgn> rgnDataList = readerService.loadRgnData();
        rgnDataList.forEach(this::saveRgn);

        System.out.println("loading rgn data comlete");
    }

    public List<Rgn> getAllRgn() {
        return rgnRepository.getAllRgn();
    }

    public List<String> searchRgnByName(String query) {
        return rgnRepository.getAllRgn().stream().
                filter(rgn -> StringUtils.containsIgnoreCase(rgn.getName(), query)).
                map(Rgn::getRgn).collect(Collectors.toList());
    }

    public String getRgnNameByCode(String code) {
        return rgnRepository.getRgnNameByCode(code);
    }

    public void saveRgn(Rgn rgn) {
        rgnRepository.saveRgn(rgn);
    }
}
