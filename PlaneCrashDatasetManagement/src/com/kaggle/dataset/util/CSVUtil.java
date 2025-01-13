package com.kaggle.dataset.util;

import com.kaggle.dataset.exception.InvalidCSVException;
import com.kaggle.dataset.model.PlaneCrashData;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kaggle.dataset.util.PrintUtil.printToConsole;

/**
 * The CSVUtil class
 */
public class CSVUtil {

    private static final String SEPARATOR = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    /**
     * Method to get CSV headers
     *
     * @return The headers
     */
    public static String[] getHeaders() {
        return ("Date,Time,Location,Operator,Flight..," +
                "Route,Type,Registration,cn.In" +
                ",Aboard,Fatalities,Ground,Survivors,SurvivalRate,Summary,ClustID")
                .split(",");
    }

    /**
     * Method to get header field map
     *
     * @return The header field map
     */
    public static Map<String, String> getHeaderFieldMap() {
        Map<String, String> headerFieldMap = new HashMap<>();
        headerFieldMap.put("Date", "date");
        headerFieldMap.put("Time", "time");
        headerFieldMap.put("Location", "location");
        headerFieldMap.put("Operator", "operator");
        headerFieldMap.put("Flight..", "flight");
        headerFieldMap.put("Route", "route");
        headerFieldMap.put("Type", "type");
        headerFieldMap.put("Registration", "registration");
        headerFieldMap.put("cn.In", "cnIn");
        headerFieldMap.put("Aboard", "aboard");
        headerFieldMap.put("Fatalities", "fatalities");
        headerFieldMap.put("Ground", "ground");
        headerFieldMap.put("Survivors", "survivors");
        headerFieldMap.put("SurvivalRate", "survivalRate");
        headerFieldMap.put("Summary", "summary");
        headerFieldMap.put("ClustID", "clustId");
        return headerFieldMap;
    }

    /**
     * Parse the CSV and read it as list of PlaneCrashData model.
     *
     * @param filePath
     * @return
     * @throws IOException
     * @throws InvalidCSVException
     */
    public static List<PlaneCrashData> readCSV(String filePath, boolean skipHeader) throws IOException, InvalidCSVException {
        List<PlaneCrashData> planeCrashDataList = new ArrayList<>();
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            Stream<String> stream = Files.lines(path);
            stream.skip(skipHeader ? 1 : 0).map(line -> line.split(SEPARATOR, -1)).forEach(fields -> {
                if (fields.length == 16) {
                    parseToBean(planeCrashDataList, fields);
                }
            });
        } else {
            throw new InvalidCSVException(String.format("CSV file doesn't exists. %s", filePath));
        }
        return planeCrashDataList;
    }

    private static void parseToBean(List<PlaneCrashData> planeCrashDataList, String[] fields) {
        try {
            LocalDate date = ("".equals(fields[0])) ? LocalDate.now() : LocalDate.parse(fields[0].strip(), new DateTimeFormatterBuilder()
                    .appendOptional(DateTimeFormatter.ofPattern("MM/dd/"))
                    .appendOptional(DateTimeFormatter.ofPattern("M/d/"))
                    .appendOptional(DateTimeFormatter.ofPattern("MM/d/"))
                    .appendOptional(DateTimeFormatter.ofPattern("M/dd/"))
                    .appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 2, LocalDate.now().minusYears(99))
                    .toFormatter());
            LocalTime time = ("".equals(fields[1])) ? null : LocalTime.parse(fields[1].strip(), new DateTimeFormatterBuilder()
                    .appendOptional(DateTimeFormatter.ofPattern("HH:mm"))
                    .appendOptional(DateTimeFormatter.ofPattern("H:mm"))
                    .appendOptional(DateTimeFormatter.ofPattern("HH:m"))
                    .appendOptional(DateTimeFormatter.ofPattern("H:m"))
                    .toFormatter());
            String location = fields[2];
            String operator = fields[3];
            String flight = fields[4];
            String route = fields[5];
            String type = fields[6];
            String registration = fields[7];
            String cnIn = fields[8];
            Integer aboard = Integer.parseInt(fields[9]);
            Integer fatalities = Integer.parseInt(fields[10]);
            Integer ground = Integer.parseInt(fields[11]);
            Integer survivors = Integer.parseInt(fields[12]);
            Double survivalRate = Double.parseDouble(fields[13]);
            String summary = fields[14];
            String clustId = fields[15];
            planeCrashDataList.add(new PlaneCrashData(date, time, location, operator, flight, route, type,
                    registration, cnIn, aboard, fatalities, ground, survivors, survivalRate, summary, clustId));
        } catch (DateTimeParseException dateTimeParseException) {
            System.out.println(String.format("Invalid date time format. Skipping record. %s. ", dateTimeParseException.getMessage()));
        }
    }

    /**
     * Method to export the data as CSV
     *
     * @param exportFileLocation
     * @param exportFileName
     * @param table
     */
    public static void exportAsCSV(String exportFileLocation, String exportFileName, String[][] table) {
        String exportFile = exportFileLocation + exportFileName + ".csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(exportFile)))) {
            for (String[] line : table) {
                bw.write(Arrays.stream(line).collect(Collectors.joining(",")));
                bw.newLine();
            }
            printToConsole(String.format("CSV exported at %s", exportFile));
        } catch (IOException e) {
            printToConsole(String.format("Error occurred while exporting CSV. %s", e.getMessage()));
        }
    }
}
