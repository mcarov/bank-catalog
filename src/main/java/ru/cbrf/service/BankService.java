package ru.cbrf.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.cbrf.domain.Bank;
import ru.cbrf.repository.BankRepository;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.cbrf.Constants.LIST_SIZE;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;
    private final DbfReaderService readerService;
    private final PznService pznService;
    private final RgnService rgnService;

    @PostConstruct
    public void createBanksTable() throws FileNotFoundException {
        List<Bank> banks = readerService.loadBanksData();
        banks.forEach(this::saveBank);

        System.out.println("loading banks data complete");
    }

    public int getBankRepoSize() {
        return bankRepository.size();
    }

    public List<Bank> getBanks(int number) {
        int offset = LIST_SIZE*(number-1);
        return bankRepository.getBanks(offset, LIST_SIZE);
    }

    public List<Bank> searchBanks(String query) {
        List<Bank> banksByBikList = bankRepository.searchBanksByBik(query);

        List<String> pznList = pznService.searchPznByName(query);
        List<Bank> banksByPznList = pznList.stream().map(bankRepository::getBanksByPzn).
                flatMap(Collection::stream).
                collect(Collectors.toList());

        List<String> rgnList = rgnService.searchRgnByName(query);
        List<Bank> banksByRgnList = rgnList.stream().map(bankRepository::getBanksByRgn).
                flatMap(Collection::stream).
                collect(Collectors.toList());

        Map<String, Bank> banksMap = Stream.of(banksByBikList, banksByPznList, banksByRgnList).
                flatMap(Collection::stream).
                collect(Collectors.toMap(Bank::getNewNum, bank -> bank, (key1, key2) -> key1));

        return new ArrayList<>(banksMap.values());
    }

    public Bank getBankByBik(String bik) {
        return bankRepository.getBankByBik(bik);
    }

    public void saveBank(Bank bank) {
        bankRepository.saveBank(bank);
    }

    public void removeBank(String bik) {
        bankRepository.removeBank(bik);
    }
}
