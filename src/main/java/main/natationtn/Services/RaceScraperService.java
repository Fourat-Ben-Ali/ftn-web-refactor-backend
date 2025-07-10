package main.natationtn.Services;

import main.natationtn.Entities.RaceResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import main.natationtn.Entities.Clubs;
import main.natationtn.Entities.Athlete;
import main.natationtn.Entities.EquipeNationale;
import main.natationtn.Repositories.ClubRepository;
import main.natationtn.Repositories.AthleteRepository;
import main.natationtn.Repositories.EquipeNationaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RaceScraperService {

    private static final Logger logger = LoggerFactory.getLogger(RaceScraperService.class);

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private EquipeNationaleRepository equipeNationaleRepository;

    private String clean(String text) {
        return text == null ? "" : text.trim().replace("\u00a0", " ");
    }

    private String[] extractGenderAndRace(String raceGenderText) {
        String text = raceGenderText.toLowerCase().replace("classement", "").trim();
        String race = raceGenderText.replace("Classement", "").replace("classement", "").trim();
        if (text.contains("messieurs")) {
            race = race.replace("Messieurs", "").replace("messieurs", "").trim();
            return new String[]{race, "Messieurs"};
        } else if (text.contains("dames")) {
            race = race.replace("Dames", "").replace("dames", "").trim();
            return new String[]{race, "Dames"};
        } else {
            return new String[]{race, ""};
        }
    }

    public List<RaceResult> getEventDetails(String url) throws IOException {
        logger.info("Starting race details scraping for URL: {}", url);

        // Check content type
        Document doc = Jsoup.connect(url).execute().parse();
        String contentType = doc.connection().response().contentType();
        if (!contentType.contains("text/") && !contentType.contains("html")) {
            logger.warn("Unsupported content type: " + contentType);
            return Collections.emptyList();  // return empty or handle accordingly
        }

        logger.info("Fetched and parsed HTML document: {}", doc);

        // Find the start_date and end_date
        String startDate = "Unknown";
        String endDate = "Unknown";
        Pattern datePattern = Pattern.compile("(\\d{2}/\\d{2}/\\d{4})");
        for (Element tag : doc.select("h2, h3, p")) {
            String text = clean(tag.text());
            Matcher matcher = datePattern.matcher(text);
            List<String> dateMatches = new ArrayList<>();
            while (matcher.find()) {
                dateMatches.add(matcher.group(1));
            }
            if (dateMatches.isEmpty()) {
                logger.warn("No dates found in the page.");
            } else if (dateMatches.size() == 1) {
                startDate = dateMatches.get(0);
                endDate = dateMatches.get(0);
                logger.info("Found a single date: {}", startDate);
            } else {
                startDate = dateMatches.get(0);
                endDate = dateMatches.get(1);
                logger.info("Found start date: {} and end date: {}", startDate, endDate);
            }
        }

        List<RaceResult> results = new ArrayList<>();
        String currentRace = "";
        String currentGender = "";
        String currentAge = "";

        for (Element elem : doc.body().getAllElements()) {
            if ("font".equals(elem.tagName()) && "+3".equals(elem.attr("size"))) {
                String currentRaceGender = clean(elem.text());
                String[] raceGender = extractGenderAndRace(currentRaceGender);
                currentRace = raceGender[0];
                currentGender = raceGender[1];
                logger.info("Detected new race: '{}' with gender: '{}'", currentRace, currentGender);
            } else if ("font".equals(elem.tagName()) && "+1".equals(elem.attr("size")) && "#c00000".equalsIgnoreCase(elem.attr("color"))) {
                currentAge = clean(elem.text());
                logger.info("Detected age group: '{}'", currentAge);
            } else if ("table".equals(elem.tagName())) {
                Elements rows = elem.select("tr");
                logger.info("Processing table with {} rows", rows.size());
                for (int i = 1; i < rows.size(); i++) { // skip header
                    Elements cells = rows.get(i).select("td");
                    if (cells.size() < 8) {
                        logger.warn("Skipping row due to insufficient columns (expected 8): {}", rows.get(i).text());
                        continue;
                    }
                    RaceResult result = new RaceResult();
                    result.setStartDate(startDate);
                    result.setEndDate(endDate);
                    result.setRace(currentRace);
                    result.setGender(currentGender);
                    result.setAge(currentAge);
                    result.setPlace(clean(cells.get(0).text()));
                    result.setName(clean(cells.get(1).text()));
                    result.setNation(clean(cells.get(2).text()));
                    result.setBirth(clean(cells.get(3).text()));
                    result.setClub(clean(cells.get(4).text()));
                    result.setTime(clean(cells.get(5).text()));
                    result.setPoints(clean(cells.get(6).text()));
                    result.setSplitTime(clean(cells.get(7).text()));
                    logger.info("Extracted race result: {}", result);
                    results.add(result);
                }
                logger.info("Added {} race results for current table.", results.size());
            }
        }
        logger.info("Scraping completed. Total results: {}", results.size());
       this.saveRaceResultsToDb ( results );
        return results;
    }


    public List<Map<String, String>> extractBlogs() {
        List<Map<String, String>> blogs = new ArrayList<>();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Uncomment for headless mode
        WebDriver driver = new ChromeDriver(options);

        try {
            for (int pageNum = 1; pageNum <= 3; pageNum++) {
                String url = "http://ftnatation.tn/pages/" + pageNum + "/?s=R%C3%A9sultats+championnat";
                driver.get(url);
                Thread.sleep(2000);

                List<WebElement> headers = driver.findElements(By.cssSelector("h2.entry-title.nebotheme-font-adaptive"));
                for (WebElement header : headers) {
                    try {
                        WebElement aTag = header.findElement(By.tagName("a"));
                        String title = aTag.getText().trim();
                        String link = aTag.getAttribute("href");
                        Map<String, String> blog = new HashMap<>();
                        blog.put("title", title);
                        blog.put("link", link);
                        blogs.add(blog);
                    } catch (Exception e) {
                        System.out.println("⚠️ Failed to extract blog link: " + e.getMessage());
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            driver.quit();
        }
        return blogs;
    }

    public List<Map<String, String>> extractBlogsAndEnrichWithResultLinks() {
        List<Map<String, String>> blogs = extractBlogs();
        if (blogs.isEmpty()) return blogs;

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Uncomment for headless mode
        WebDriver driver = new ChromeDriver(options);

        try {
            for (Map<String, String> blog : blogs) {
                driver.get(blog.get("link"));
                Thread.sleep(2000);

                String resultLink = "Not found";
                try {
                    WebElement contentDiv = driver.findElement(By.className("site-content"));
                    List<WebElement> links = contentDiv.findElements(By.tagName("a"));
                    for (WebElement linkElem : links) {
                        String linkText = linkElem.getText().toLowerCase();
                        if (linkText.contains("résultats") || linkText.contains("resultats")) {
                            resultLink = linkElem.getAttribute("href");
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("⚠️ Could not extract résultats link from blog: " + e.getMessage());
                }
                blog.put("result_link", resultLink);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            driver.quit();
        }
        return blogs;
    }

    public List<RaceResult> scrapeAllEventsFromBlogs() {
        List<RaceResult> allResults = new ArrayList<>();
        List<Map<String, String>> blogs = extractBlogsAndEnrichWithResultLinks();

        for (Map<String, String> blog : blogs) {
            String resultLink = blog.get("result_link");
            if (resultLink == null || "Not found".equals(resultLink)) {
                logger.warn("⚠️ No result link found for blog: {}", blog.get("link"));
                continue;
            }
            // Check if result link is an image URL
            if (resultLink.endsWith(".png") || resultLink.endsWith(".jpg") || resultLink.endsWith(".jpeg") || resultLink.endsWith(".gif")) {
                logger.warn("⚠️ Skipping image URL: {}", resultLink);
                continue;  // Skip image URLs
            }

            logger.info("Processing blog: {} - {}", blog.get("title"), resultLink);
            try {
                List<RaceResult> results = getEventDetails(resultLink);
                allResults.addAll(results);
            } catch (Exception e) {
                logger.error("Failed to scrape event details for link: " + resultLink, e);
            }
        }
        return allResults;
    }

    @Transactional
    public void saveRaceResultsToDb(List<RaceResult> results) {
        for (RaceResult result : results) {
            // 1. Club
            System.out.println("start");

            Clubs club = null;
            if (result.getClub() != null && !result.getClub().isEmpty()) {
                club = clubRepository.findByClubName(result.getClub())
                        .orElseGet(() -> {
                            Clubs newClub = new Clubs();
                            newClub.setClubName(result.getClub());
                            return clubRepository.save(newClub);
                        });
            }

            // 2. Equipe Nationale (optionnel, selon ta logique)
            EquipeNationale equipe = null;
            if (result.getNation() != null && !result.getNation().isEmpty()) {
                equipe = equipeNationaleRepository.findByNom(result.getNation())
                        .orElseGet(() -> {
                            EquipeNationale newEquipe = new EquipeNationale();
                            newEquipe.setNom(result.getNation());
                            return equipeNationaleRepository.save(newEquipe);
                        });
            }

            // 3. Athlete

            String [] fullName  = this.splitNomPrenom ( result.getName ( ) );
            String year = result.getBirth();
            String dateString = "01-01-" + year;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dateNaissance = LocalDate.parse(dateString, formatter);

            Athlete athlete = new Athlete();
            athlete.setPrenom(fullName[1]);
            athlete.setNom(fullName[0]);
            athlete.setGenre(result.getGender());
             athlete.setDateNaissance(dateNaissance);
            athlete.setNationalite(result.getNation());
            athlete.setClub(club);
            athlete.setEquipeNationale(equipe);

            // Vérifier s'il existe déjà (par exemple par nom/prenom/date_naissance)
            // Sinon, sauvegarder
            athleteRepository.save(athlete);
        }
    }

    public static String[] splitNomPrenom(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return new String[]{"", ""};
        }
        String[] parts = fullName.trim().split("\\s+");
        StringBuilder nom = new StringBuilder();
        String prenom = "";

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (part.equals(part.toUpperCase())) {
                // Mot tout en majuscule = nom
                if (nom.length() > 0) nom.append(" ");
                nom.append(part);
            } else {
                // Premier mot non tout en majuscule = prénom (et tout ce qui suit)
                prenom = String.join(" ", Arrays.copyOfRange(parts, i, parts.length));
                break;
            }
        }
        return new String[]{nom.toString(), prenom};
    }
}
