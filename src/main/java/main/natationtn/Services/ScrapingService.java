package main.natationtn.Services;

import main.natationtn.Entities.CalendarResponse;
import main.natationtn.Entities.CompetitionEvent;
import main.natationtn.Entities.QualificationRule;
import org.springframework.stereotype.Service;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class ScrapingService {

    private static final String URL_CLUBS = "http://ftnatation.tn/licences-2021-2022/";
    private final String PDF_URL = "http://ftnatation.tn/wp-content/uploads/2025/04/calendrier-international-2025_removed.pdf";

    public List<String> getClubs() throws IOException {
        List<String> clubs = new ArrayList<>();

        Document doc = Jsoup.connect(URL_CLUBS).get();

        Elements clubElements = doc.select("p.has-nebotheme-content-color.has-text-color a");

        for (Element element : clubElements) {
            String clubName = element.text();
            String clubLink = element.attr("href");
            clubs.add(clubName + " - " + clubLink);
        }

        return clubs;
    }


    public String scrapeCalendrierInternational() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            byte[] pdfBytes = restTemplate.getForObject(PDF_URL, byte[].class);

            InputStream inputStream = new ByteArrayInputStream(pdfBytes);
            PDDocument document = PDDocument.load(inputStream);
            PDFTextStripper stripper = new PDFTextStripper();

            String text = stripper.getText(document);
            document.close();

            return text;
        } catch (Exception e) {
            return "Erreur lors du scraping du PDF : " + e.getMessage();
        }
    }
    public CalendarResponse parseCalendar() throws IOException {
        List<CompetitionEvent> events = new ArrayList<>();
        List<QualificationRule> qualifications = new ArrayList<>();

        try (PDDocument document = PDDocument.load(new URL("http://ftnatation.tn/wp-content/uploads/2025/04/calendrier-international-2025_removed.pdf").openStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            String[] parts = text.split("Qualification :");
            String[] lines = parts[0].split("\\r?\\n");

            CompetitionEvent currentEvent = null;
            for (String line : lines) {
                if (line.matches("^\\d+/.*")) {
                    if (currentEvent != null) events.add(currentEvent);
                    currentEvent = new CompetitionEvent();
                    currentEvent.setTitle(line.replaceAll("^\\d+/", "").trim());
                } else if (line.contains("du") || line.contains("décembre")) {
                    if (currentEvent != null) currentEvent.setLocationAndName(line.trim());
                }

            }
            if (currentEvent != null) events.add(currentEvent);

            if (parts.length > 1) {
                String[] qualLines = parts[1].split("\\r?\\n");
                String currentCat = null;
                List<String> currentRules = new ArrayList<>();

                for (String line : qualLines) {
                    if (line.matches("^[A-C]/.*")) {
                        if (currentCat != null) {
                            qualifications.add(new QualificationRule(currentCat, new ArrayList<>(currentRules)));
                        }
                        currentCat = line.trim();
                        currentRules.clear();
                    } else if (!line.isBlank()) {
                        currentRules.add(line.trim());
                    }
                }
                if (currentCat != null) {
                    qualifications.add(new QualificationRule(currentCat, currentRules));
                }
            }
        }

        return new CalendarResponse(events, qualifications);
    }

    private String extractDateFromLine(String line) {
        int indexDu = line.indexOf("du ");
        if (indexDu != -1) {
            return line.substring(indexDu).trim();
        }

        if (line.toLowerCase().contains("décembre")) {
            return line.trim();
        }

        return "";
    }

}



