package io.utility.coronavirustracker.services;

import io.utility.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    private static String VIRUS_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    private List<LocationStats> allLocationStatsList = new ArrayList<>(); // added for concurrency purpose

    public List<LocationStats> getAllLocationStatsList() {
        return allLocationStatsList;
    }

    @PostConstruct
    @Scheduled(cron = "* 59 * * * *")
    public void fetchVirusData() throws IOException, InterruptedException {
        List<LocationStats> newLocationStatsList = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_URL)).build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader in = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCount = Integer.parseInt(record.get( record.size() - 1));
            int previousCount = Integer.parseInt(record.get( record.size() - 2));
            locationStat.setTotalCaseReported(latestCount);
            locationStat.setIncreaseFromPreviousDay(latestCount - previousCount);
            newLocationStatsList.add(locationStat);
        }

        this.allLocationStatsList = newLocationStatsList;
    }
}
