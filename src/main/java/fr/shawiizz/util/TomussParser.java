package fr.shawiizz.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.shawiizz.models.*;
import fr.shawiizz.models.tomuss.TomussGradesModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TomussParser {

    private static final ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());

    /**
     * Extracts the data array from the HTML page
     * The grades array is a JSON5 string inside a JS function call
     *
     * @param html The HTML page
     */
    public static List<Object> extractDataArray(String html) throws IOException {
        // Replace \x3E with nothing because it causes issues with the JSON parser
        String arrayArg = html.split("display_update\\(")[1].split(",\"Top\"")[0].replace("\\x3E", "");
        return objectMapper.enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature()).readValue(arrayArg, List.class);
    }

    /**
     * Extracts the grades array from the HTML page
     *
     * @param html The HTML page
     */
    public static List<TomussGradesModel.TomussGradeElement> extractGradesArray(String html) throws IOException {
        for (Object item : extractDataArray(html)) {
            List<Object> itemList = (List<Object>) item;
            if ("Grades".equals(itemList.get(0))) {
                ArrayList<TomussGradesModel.TomussGradeElement> grades = new ArrayList<>();
                for (Object grade : (ArrayList) ((ArrayList) itemList.get(1)).get(0)) {
                    grades.add(objectMapper.convertValue(grade, TomussGradesModel.TomussGradeElement.class));
                }
                return grades;
            }
        }
        return null;
    }

    /**
     * Extracts the semesters array from the HTML page
     *
     * @param html The HTML page
     * @returns The semesters Map
     * e.g. "2022/Automne" -> "https://tomuss.univ-lyon1.fr/S/2022/Automne"
     * "2023/Printemps" -> "https://tomuss.univ-lyon1.fr/S/2023/Printemps"
     */
    public static Map<String, String> extractSemestersArray(String html) throws IOException {
        for (Object item : extractDataArray(html)) {
            List<Object> itemList = (List<Object>) item;
            if ("Semesters".equals(itemList.get(0))) {
                return objectMapper.convertValue(itemList.get(1), Map.class);
            }
        }
        return null;
    }

    /**
     * Parses a module light from the JSON5 string
     *
     * @param moduleJson The grade element (JSON5 string)
     * @returns The module light object
     */
    public static TomussModuleLight parseModuleLight(TomussGradesModel.TomussGradeElement moduleJson) {
        return new TomussModuleLight(moduleJson.table_title, moduleJson.ue);
    }

    /**
     * Parses a module from the JSON5 string
     *
     * @param moduleJson The grade element (JSON5 string)
     * @returns The module object
     */
    public static TomussModule parseModule(TomussGradesModel.TomussGradeElement moduleJson) {
        List<NoteColumnWithPosition> noteColumnsWithPosition = new ArrayList<>();

        for (int i = 0; i < moduleJson.columns.size(); i++) {
            TomussGradesModel.TomussColumn column = moduleJson.columns.get(i);
            if (TomussGradesModel.TomussType.isType(column.type, TomussGradesModel.TomussType.Note) || TomussGradesModel.TomussType.isType(column.type, TomussGradesModel.TomussType.Moy) || TomussGradesModel.TomussType.isType(column.type, TomussGradesModel.TomussType.Replace)) {
                noteColumnsWithPosition.add(new NoteColumnWithPosition(i, column));
            }
        }

        List<Grade> notes = new ArrayList<>();

        for (NoteColumnWithPosition noteColumnWithPosition : noteColumnsWithPosition) {
            TomussGradesModel.TomussStat stats = moduleJson.stats.get(noteColumnWithPosition.column.the_id);
            List<Object> note = moduleJson.line.get(noteColumnWithPosition.position);

            // Note not valid or not set yet
            if (note == null || note.isEmpty() || !(note.get(0) instanceof Number)) continue;

            double noteIsOn = noteColumnWithPosition.column.minmax != null ? Double.parseDouble(noteColumnWithPosition.column.minmax.split(";")[1].split("]")[0]) : 20;

            notes.add(new Grade(
                    noteColumnWithPosition.column.title,
                    noteColumnWithPosition.column.comment,
                    new Mark(((Number) note.get(0)).doubleValue(), noteIsOn),
                    (String) note.get(1),
                    note.size() > 2 ? TomussTransformer.tomussDateToDate((String) note.get(2)) : null,
                    new Stats(
                            stats.average,
                            stats.mediane,
                            new StatsGroup(stats.nr_in_grp, stats.rank_grp + 1), // +1 because the rank starts at 0
                            new StatsPromo(stats.nr, stats.rank + 1) // +1 because the rank starts at 0
                    ),
                    TomussGradesModel.TomussType.isType(noteColumnWithPosition.column.type, TomussGradesModel.TomussType.Moy),
                    noteColumnWithPosition.column.weight != null ? Double.parseDouble(noteColumnWithPosition.column.weight) : 1
            ));
        }

        return new TomussModule(moduleJson.table_title, moduleJson.ue, notes);
    }

    /**
     * Extracts the Apogee code from the UE string
     * The UE string can be in the format "UE-APOGEE_CODE@XXX" or "APOGEE_CODE@XXX"
     * This function will return "APOGEE_CODE"
     *
     * @param ueName The UE string
     * @return The APOGEE code
     */
    public static String getApogeeCode(String ueName) {
        String ueExtracted = ueName;
        String[] firstPart = ueName.split("-");

        if (firstPart.length > 1)
            ueExtracted = firstPart[1].trim();

        String[] secondPart = ueExtracted.split("@");

        if (secondPart.length > 1)
            ueExtracted = secondPart[0].trim();

        return ueExtracted;
    }

    private static class NoteColumnWithPosition {
        int position;
        TomussGradesModel.TomussColumn column;

        NoteColumnWithPosition(int position, TomussGradesModel.TomussColumn column) {
            this.position = position;
            this.column = column;
        }
    }
}