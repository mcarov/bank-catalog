package ru.cbrf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.cbrf.domain.Bank;
import ru.cbrf.model.BankModel;
import ru.cbrf.service.*;

import java.util.Map;

import static ru.cbrf.Constants.LIST_SIZE;

@Controller
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;
    private final PznService pznService;
    private final UerService uerService;
    private final RgnService rgnService;
    private final TnpService tnpService;

    @GetMapping("/")
    public String getMainPage(Model model) {
        int repoSize = bankService.getBankRepoSize();
        int lastNum = (int)Math.ceil(repoSize*1.0/LIST_SIZE);

        model.addAllAttributes(Map.of(
                "banks", bankService.getBanks(1),
                "page", 1,
                "last", lastNum,
                "size", repoSize));

        return "banks";
    }

    @GetMapping("/page/{num}")
    public String getBanks(Model model, @PathVariable int num) {
        int repoSize = bankService.getBankRepoSize();
        int lastNum = (int)Math.ceil(repoSize*1.0/LIST_SIZE);

        model.addAllAttributes(Map.of(
                "banks", bankService.getBanks(num),
                "page", num,
                "last", lastNum,
                "size", repoSize));

        return "banks";
    }

    @GetMapping("/banks/{bik}")
    public String getBank(Model model, @PathVariable String bik) {
        Bank bank = bankService.getBankByBik(bik);
        model.addAllAttributes(Map.of(
                "bank", bank,
                "pzn", pznService.getPznNameByCode(bank.getPzn()),
                "uer", uerService.getUerNameByCode(bank.getUer()),
                "rgn", rgnService.getRgnNameByCode(bank.getRgn()),
                "tnp", tnpService.getTnpNameByCode(bank.getTnp())
        ));

        return "bank";
    }

    @GetMapping("/banks/{bik}/edit")
    public String editBank(Model model, @PathVariable String bik) {
        if(!bik.equals("0")) {
            model.addAllAttributes(Map.of(
                    "bank", bankService.getBankByBik(bik),
                    "pznList", pznService.getAllPzn(),
                    "uerList", uerService.getAllUer(),
                    "rgnList", rgnService.getAllRgn(),
                    "tnpList", tnpService.getAllTnp()
            ));
        }
        else {
            model.addAllAttributes(Map.of(
                    "pznList", pznService.getAllPzn(),
                    "uerList", uerService.getAllUer(),
                    "rgnList", rgnService.getAllRgn(),
                    "tnpList", tnpService.getAllTnp()
            ));
        }

        return "edit";
    }

    @GetMapping(value = "/search", params = {"q"})
    public String doSearch(Model model, @RequestParam String q) {
        model.addAttribute("banks", bankService.searchBanks(q));
        model.addAllAttributes(Map.of(
                "banks", bankService.searchBanks(q),
                "query", q
        ));

        return "search";
    }

    @GetMapping("banks/{bik}/remove")
    public String removeBankConfirm(Model model, @PathVariable String bik) {
        model.addAttribute("bank", bankService.getBankByBik(bik));

        return "remove";
    }

    @PostMapping("banks/{bik}/remove")
    public String removeBank(@PathVariable String bik) {
        bankService.removeBank(bik);

        return "redirect:/";
    }

    @PostMapping("banks/{bik}/save")
    public String saveBank(@PathVariable String bik, @ModelAttribute BankModel bankModel) {
        Bank bank = bankModel.getBankBuilder().build();
        bankService.saveBank(bank);

        return bik.equals("0") ? "redirect:/" : String.join("/","redirect:", "banks", bank.getNewNum());
    }
}
