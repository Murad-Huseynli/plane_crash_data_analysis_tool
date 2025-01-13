package com.kaggle.dataset.util;

import com.kaggle.dataset.model.Options;
import com.kaggle.dataset.model.PlaneCrashData;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The print util class
 */
public class PrintUtil {

    /**
     * Method to format and print the csv data
     *
     * @param planeCrashDataList
     * @param options
     */
    public static void formatAndPrint(List<PlaneCrashData> planeCrashDataList, Options options) {

        Map<String, String> headerFieldMap = CSVUtil.getHeaderFieldMap();

        int skip = 0;
        int limit = planeCrashDataList.size();
        if("range".equalsIgnoreCase(options.getFieldOption())) {
            skip = options.getStartRange();
            limit = options.getEndRange() - skip;
        }

        String[] displayHeaders = getDisplayHeaders(options);
        Comparator<PlaneCrashData> sortingOrder = getSortingOrder(options, headerFieldMap);
        Predicate<PlaneCrashData> searchPredicate = SearchAndFilterUtil.getSearchPredicate(options, headerFieldMap);
        Predicate<PlaneCrashData> filterPredicate = SearchAndFilterUtil.getFilterPredicate(options, headerFieldMap);

        List<String[]> planeCrashDataArray = planeCrashDataList.stream()
                .skip(skip)
                .limit(limit)
                .sorted(sortingOrder)
                .filter(searchPredicate)
                .filter(filterPredicate)
                // Map to table
                .map(planeCrashData ->
                        Arrays.stream(displayHeaders)
                                .map(header -> {
                                    try {
                                        Field field = PlaneCrashData.class.getDeclaredField(headerFieldMap.get(header));
                                        field.setAccessible(true);
                                        String fieldValue = (field.get(planeCrashData) != null) ? field.get(planeCrashData).toString() : "";
                                        return fieldValue;
                                    } catch (NoSuchFieldException | IllegalAccessException e) {
                                        printToConsole(e.getMessage());
                                    }
                                    return "";
                                }).toArray(String[]::new)).collect(Collectors.toList());
        planeCrashDataArray.add(0, displayHeaders);
        String[][] table = planeCrashDataArray.stream().toArray(String[][]::new);

        if (options.isExportAsCSV()) {
            CSVUtil.exportAsCSV(options.getInputFileLocation(), options.getExportFileName(), table);
        } else {
            printTable(table);
        }
    }

    private static String[] getDisplayHeaders(Options options) {
        // Listing the fields
        String[] tmpHeaders = CSVUtil.getHeaders();
        String fieldOption = options.getFieldOption();
        if ("selected".equalsIgnoreCase(fieldOption)) {
            String[] fieldsToShow = options.getFieldsToShow();
            tmpHeaders = Arrays.stream(CSVUtil.getHeaders())
                    .filter(header -> Arrays.stream(fieldsToShow).anyMatch(field -> field.equalsIgnoreCase(header)))
                    .toArray(String[]::new);
        }
        return tmpHeaders;
    }

    private static Comparator<PlaneCrashData> getSortingOrder(Options options, Map<String, String> headerFieldMap) {
        String sortBy = options.getSortBy();
        String sortOrder = options.getSortOrder();
        if (sortBy != null) {
            return (o1, o2) -> {
                try {
                    Field field = PlaneCrashData.class.getDeclaredField(headerFieldMap.get(sortBy));
                    field.setAccessible(true);
                    if (field.getType().equals(LocalDate.class)) {
                        LocalDate field1 = (LocalDate) field.get(o1);
                        LocalDate field2 = (LocalDate) field.get(o2);
                        if ("asc".equalsIgnoreCase(sortOrder)) {
                            if(field1 == null)
                                return -1;
                            if(field2 == null)
                                return 1;
                            return field1.compareTo(field2);
                        } else {
                            if(field2 == null)
                                return -1;
                            if(field1 == null)
                                return 1;
                            return field2.compareTo(field1);
                        }
                    } else if (field.getType().equals(LocalTime.class)) {
                        LocalTime field1 = (LocalTime) field.get(o1);
                        LocalTime field2 = (LocalTime) field.get(o2);
                        if ("asc".equalsIgnoreCase(sortOrder)) {
                            if(field1 == null)
                                return -1;
                            if(field2 == null)
                                return 1;
                            return field1.compareTo(field2);
                        } else {
                            if(field2 == null)
                                return -1;
                            if(field1 == null)
                                return 1;
                            return field2.compareTo(field1);
                        }
                    } else if (field.getType().equals(Integer.class)) {
                        Integer field1 = (Integer) field.get(o1);
                        Integer field2 = (Integer) field.get(o2);
                        if ("asc".equalsIgnoreCase(sortOrder)) {
                            return field1.compareTo(field2);
                        } else {
                            return field2.compareTo(field1);
                        }
                    } else {
                        if ("asc".equalsIgnoreCase(sortOrder)) {
                            return field.get(o1).toString().compareTo(field.get(o2).toString());
                        } else {
                            return field.get(o2).toString().compareTo(field.get(o1).toString());
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    printToConsole(String.format("Error. %s", e.getMessage()));
                }
                return o1.getDate().compareTo(o2.getDate());
            };
        }

        return Comparator.comparing(PlaneCrashData::getDate);
    }

    private static void printTable(String[][] table) {
        /*
         * Calculate appropriate Length of each column by looking at width of data in
         * each column.
         *
         * Map columnLengths is <column_number, column_length>
         */
        Map<Integer, Integer> columnLengths = new HashMap<>();
        Arrays.stream(table).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
            if (columnLengths.get(i) == null) {
                columnLengths.put(i, 0);
            }
            if (columnLengths.get(i) < a[i].length()) {
                columnLengths.put(i, a[i].length());
            }
        }));
        //System.out.println("columnLengths = " + columnLengths);

        /*
         * Prepare format String
         */
        boolean alignLeftRows = true;
        final StringBuilder formatString = new StringBuilder("");
        String theFlag = alignLeftRows ? "-" : "";
        columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + theFlag + e.getValue() + "s "));
        formatString.append("|\n");

        /*
         * Prepare line for top, bottom & below header row.
         */
        String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
            String templn = "+-";
            templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-",
                    (a1, b1) -> a1 + b1);
            templn = templn + "-";
            return ln + templn;
        }, (a, b) -> a + b);
        line = line + "+\n";

        /*
         * Print table
         */
        System.out.print(line);
        Arrays.stream(table).limit(1).forEach(a -> System.out.printf(formatString.toString(), a));
        System.out.print(line);

        Stream.iterate(1, (i -> i < table.length), (i -> ++i))
                .forEach(a -> System.out.printf(formatString.toString(), table[a]));
        System.out.print(line);

        printToConsole(String.format("Total records: %s", table.length - 1));
    }

    /**
     * Method to print the string to console
     *
     * @param message
     */
    public static void printToConsole(String message) {
        System.out.println(message);
    }
}
