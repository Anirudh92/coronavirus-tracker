package io.utility.coronavirustracker.controllers;

import io.utility.coronavirustracker.models.LocationStats;
import io.utility.coronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home( Model model ){
        List<LocationStats> allStats = coronaVirusDataService.getAllLocationStatsList();
        int totalConfirmedCases = allStats.stream().mapToInt(stat -> stat.getTotalCaseReported()).sum();
        int totalConfirmedCasesPreviousDay = allStats.stream().mapToInt(stat -> stat.getIncreaseFromPreviousDay()).sum();
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalConfirmedCases", totalConfirmedCases);
        model.addAttribute("totalConfirmedCasesPreviousDay", totalConfirmedCasesPreviousDay);

        return "home";
    }
}
