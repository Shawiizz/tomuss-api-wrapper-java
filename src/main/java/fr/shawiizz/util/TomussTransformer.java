package fr.shawiizz.util;

import fr.shawiizz.models.TomussModule;
import fr.shawiizz.models.TomussModuleLight;
import fr.shawiizz.models.tomuss.TomussGradesModel;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TomussTransformer {

    /**
     * Converts a TOMUSS grades array to a modules array
     *
     * @param gradesArray The TOMUSS grades array
     */
    public static List<TomussModule> tomussGradesToModules(List<TomussGradesModel.TomussGradeElement> gradesArray) {
        return gradesArray.stream()
                .map(TomussParser::parseModule)
                .collect(Collectors.toList());
    }

    /**
     * Converts a TOMUSS grades array to a modules light array
     *
     * @param gradesArray The TOMUSS grades array
     * @return The modules light array
     */
    public static List<TomussModuleLight> tomussGradesToModulesLight(List<TomussGradesModel.TomussGradeElement> gradesArray) {
        return gradesArray.stream()
                .map(TomussParser::parseModuleLight)
                .collect(Collectors.toList());
    }

    /**
     * Converts a TOMUSS date to a LocalDate object
     *
     * @param tomussDate The TOMUSS date (YYYYMMDDHHmmss)
     */
    public static Instant tomussDateToDate(String tomussDate) {
        if (tomussDate == null || tomussDate.isEmpty()) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(tomussDate, formatter);

        return dateTime.toInstant(ZoneId.systemDefault().getRules().getOffset(Instant.now()));
    }
}