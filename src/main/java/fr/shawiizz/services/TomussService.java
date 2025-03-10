package fr.shawiizz.services;

import fr.shawiizz.models.TomussModule;
import fr.shawiizz.models.TomussModuleLight;
import fr.shawiizz.util.Semester;
import fr.shawiizz.util.TomussParser;
import fr.shawiizz.util.TomussTransformer;
import fr.shawiizz.util.enums.Season;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TomussService {
    private final CASAuthService authService;

    private TomussService(CASAuthService authService) {
        this.authService = authService;
    }

    /**
     * Use tomuss service with the cas authentication (only auth system for now)
     *
     * @param authService The CASAuthService
     * @return The TomussService
     */
    public static TomussService withAuth(CASAuthService authService) {
        return new TomussService(authService);
    }

    /**
     * Get the TOMUSS page for the given semester
     *
     * @param semester The semester
     * @return The TOMUSS page content as a string
     */
    public String getTomussPage(Semester semester) throws IOException, InterruptedException {
        Pattern regexUrl = Pattern.compile("https://[^\"]+");
        Pattern regexCountdown = Pattern.compile("id=\"t\">(\\d+\\.\\d+)");

        String redirectUrl = authService.serviceRedirect(semester.getTomussHomeUrl(), true);
        Matcher redirectMatch = regexUrl.matcher(redirectUrl);
        if (!redirectMatch.find()) {
            throw new IllegalArgumentException("Could not find the redirect URL");
        }

        String tomussPageContent = authService.getPage(redirectMatch.group(0));

        Matcher countdownMatch = regexCountdown.matcher(tomussPageContent);
        if (!countdownMatch.find()) return tomussPageContent;

        // Wait for the countdown to end
        if (Float.parseFloat(countdownMatch.group(1)) > 0) {
            TimeUnit.SECONDS.sleep((long) Float.parseFloat(countdownMatch.group(1)));
        }

        return this.getTomussPage(semester);
    }

    /**
     * Get the modules of the given semesters with grades
     *
     * @param semesters The semesters
     */
    public List<TomussModule> getModules(Semester... semesters) throws IOException, InterruptedException {
        List<TomussModule> modules = new ArrayList<>();

        for (Semester sem : semesters) {
            String page = getTomussPage(sem);
            modules.addAll(TomussTransformer.tomussGradesToModules(TomussParser.extractGradesArray(page)));
        }

        return modules;
    }

    /**
     * Get the modules light of the given semesters with grades
     * Light modules are modules without grades, and it's a way to get all UE and module names
     *
     * @param semesters The semesters
     */
    public List<TomussModuleLight> getModulesListLight(Semester... semesters) throws IOException, InterruptedException {
        List<TomussModuleLight> modules = new ArrayList<>();

        for (Semester sem : semesters) {
            String page = getTomussPage(sem);
            modules.addAll(TomussTransformer.tomussGradesToModulesLight(TomussParser.extractGradesArray(page)));
        }

        return modules;
    }

    /**
     * Get the available semesters for the user
     *
     * @return The available semesters (returns Semester objects that can be used with getModules for example)
     */
    public List<Semester> getAvailableSemesters() throws IOException, InterruptedException {
        String homePageContent = getTomussPage(Semester.current());
        return TomussParser.extractSemestersArray(homePageContent).keySet().stream()
                .map(yearSlashSemesterName -> {
                    String[] parts = yearSlashSemesterName.split("/");
                    int year = Integer.parseInt(parts[0]);
                    Season season = Season.valueOf(parts[1].toUpperCase());
                    return Semester.fromYearAndSeason(year, season);
                })
                .collect(Collectors.toList());
    }
}