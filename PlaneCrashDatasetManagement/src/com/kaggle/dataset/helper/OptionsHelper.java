package com.kaggle.dataset.helper;

import com.kaggle.dataset.model.Options;
import com.kaggle.dataset.model.PlaneCrashData;
import com.kaggle.dataset.util.CSVUtil;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.kaggle.dataset.util.PrintUtil.printToConsole;

/**
 * Helper class to get the option values from the user
 */
public class OptionsHelper {

    /**
     * Method to collect initial options from the user
     *
     * @return The options
     */

    public static Options collectInitialOptions() {
        Options options = collectFieldOptions();
        options = collectSortingOptions(options);
        return options;
    }

    /**
     * Method to collect initial field options
     *
     * @return The options
     */
    private static Options collectFieldOptions() {
        Options options = new Options();
        printToConsole("Please, choose an option you would like to work with:");
        printToConsole("a. List all the fields of each entity");
        printToConsole("b. List only the selected fields of each entity");
        printToConsole("c. List entities based on the range of rows");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        switch (input) {
            case "a":
                options.setFieldOption("all");
                break;
            case "b":
                options.setFieldOption("selected");
                printToConsole("Please provide the columns to be displayed as a comma separated input. For example Date,Time,Location,Operator,Flight..");
                printToConsole("Valid column names are:");
                printToConsole(Arrays.stream(CSVUtil.getHeaders()).collect(Collectors.joining(",")));
                input = in.nextLine();
                options.setFieldsToShow(input.split(","));
                break;
            case "c":
                options.setFieldOption("range");
                printToConsole("Please provide the start & end range to be displayed");
                printToConsole("Please enter starting index. Initial index is 0");
                int startingIndex = in.nextInt();
                options.setStartRange(startingIndex);

                printToConsole("Please enter ending index");
                int endingIndex = in.nextInt();
                options.setEndRange(endingIndex);
                break;
        }

        return options;
    }

    /**
     * Method to collect sorting options
     *
     * @param options
     * @return The options
     */
    private static Options collectSortingOptions(Options options) {
        printToConsole("Do you want to sort the table(Y/n)?");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if ("y".equalsIgnoreCase(input)) {
            printToConsole("Please type the column name with which you want to sort the table.");
            printToConsole("Valid column names are:");
            printToConsole(Arrays.stream(CSVUtil.getHeaders()).collect(Collectors.joining(",")));
            in = new Scanner(System.in);
            input = in.nextLine();
            options.setSortBy(input);

            printToConsole("Please specify the sorting order(Asc/Desc)");
            in = new Scanner(System.in);
            input = in.nextLine();
            options.setSortOrder(input);
        }
        return options;
    }

    /**
     * Method to collect additional options from the user
     *
     * @param options
     * @return The options
     */
    public static Options collectMoreOptions(Options options) {
        printToConsole("More options:");
        printToConsole("a. Search");
        printToConsole("b. Filter");
        printToConsole("c. Export as CSV");
        printToConsole("d. Back to main menu");
        printToConsole("e. Exit");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        switch (input) {
            case "a":
                options.setFilterField(null);
                options.setSearchField(null);
                options.setExportAsCSV(false);

                printToConsole("Please provide the column name to search");
                printToConsole("Valid column names are:");
                printToConsole(Arrays.stream(CSVUtil.getHeaders()).collect(Collectors.joining(",")));
                in = new Scanner(System.in);
                input = in.nextLine();
                options.setSearchField(input);

                printToConsole("Please provide search criteria. If you are searching for date or time then please enter date in yyyy-MM-dd & time in HH:mm format");
                in = new Scanner(System.in);
                input = in.nextLine();
                options.setSearchCriteria(input);
                break;
            case "b":
                options.setFilterField(null);
                options.setSearchField(null);
                options.setExportAsCSV(false);
                return collectFilterOptions(options);
            case "c":
                printToConsole("Please provide the export file name (default export.csv)");
                in = new Scanner(System.in);
                input = in.nextLine();

                input = (input == null||"".equals(input)) ? "export" : input;
                options.setExportAsCSV(true);
                options.setExportFileName(input);
                break;
            case "d":
                options = null;
                break;
            default:
                System.exit(0);
        }
        return options;
    }

    private static Options collectFilterOptions(Options options) {
        printToConsole("Please provide the column name to filter");
        printToConsole("Valid column names are:");
        printToConsole(Arrays.stream(CSVUtil.getHeaders()).collect(Collectors.joining(",")));
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        options.setFilterField(input);

        Map<String, String> headerFieldMap = CSVUtil.getHeaderFieldMap();
        try {
            Field field = PlaneCrashData.class.getDeclaredField(headerFieldMap.get(input));
            field.setAccessible(true);
            if (field.getType().equals(LocalDate.class) || field.getType().equals(LocalTime.class)
                    || field.getType().equals(Integer.class) || field.getType().equals(Double.class)) {
                filterOptionsForDateAndNumbers(options, field.getType());
            } else if (field.getType().equals(String.class)) {
                printToConsole("Please select the filter criteria");
                printToConsole("a. Starts With");
                printToConsole("b. Ends With");
                printToConsole("c. Contains");
                printToConsole("d. Is Null");
                in = new Scanner(System.in);
                input = in.nextLine();
                switch (input) {
                    case "a":
                        options.setFilterCriteria("starts_with");
                        break;
                    case "b":
                        options.setFilterCriteria("ends_with");
                        break;
                    case "c":
                        options.setFilterCriteria("contains");
                        break;
                    case "d":
                        options.setFilterCriteria("is_null");
                        break;
                }

                if (!"is_null".equalsIgnoreCase(options.getFilterCriteria())) {
                    printToConsole("Please enter the value to apply the criteria");
                    in = new Scanner(System.in);
                    input = in.nextLine();
                    options.setFilterValue(input);
                }
            }
        } catch (NoSuchFieldException e) {
            printToConsole(String.format("Error. %s", e.getMessage()));
        }
        return options;
    }

    private static void filterOptionsForDateAndNumbers(Options options, Class clazz) {
        Scanner in;
        String input;
        printToConsole("Please select the filter criteria. If you are filtering for date or time then please enter date in yyyy-MM-dd & time in HH:mm format");
        printToConsole("a. Equals");
        printToConsole("b. Greater than");
        printToConsole("c. Less than");
        printToConsole("d. Greater than & equal");
        printToConsole("e. Less than & equal");
        printToConsole("f. Between");
        printToConsole("g. Is Null");
        if (clazz.equals(LocalDate.class)) {
            printToConsole("h. In a specific year");
            printToConsole("i. In a specific month");
            printToConsole("j. In a specific day");
        }
        in = new Scanner(System.in);
        input = in.nextLine();
        switch (input) {
            case "a":
                options.setFilterCriteria("equals");
                break;
            case "b":
                options.setFilterCriteria("greater_than");
                break;
            case "c":
                options.setFilterCriteria("less_than");
                break;
            case "d":
                options.setFilterCriteria("greater_than_equal");
                break;
            case "e":
                options.setFilterCriteria("less_than_equal");
                break;
            case "f":
                options.setFilterCriteria("between");
                break;
            case "g":
                options.setFilterCriteria("is_null");
                break;
            case "h":
                options.setFilterCriteria("specific_year");
                break;
            case "i":
                options.setFilterCriteria("specific_month");
                break;
            case "j":
                options.setFilterCriteria("specific_day");
                break;
        }
        if (!"is_null".equalsIgnoreCase(options.getFilterCriteria())) {
            if ("equals".equalsIgnoreCase(options.getFilterCriteria())
                    || "greater_than".equalsIgnoreCase(options.getFilterCriteria())
                    || "less_than".equalsIgnoreCase(options.getFilterCriteria())
                    || "greater_than_equal".equalsIgnoreCase(options.getFilterCriteria())
                    || "less_than_equal".equalsIgnoreCase(options.getFilterCriteria())
                    || "specific_year".equalsIgnoreCase(options.getFilterCriteria())
                    || "specific_month".equalsIgnoreCase(options.getFilterCriteria())
                    || "specific_day".equalsIgnoreCase(options.getFilterCriteria())) {
                printToConsole("Please enter the value to apply the criteria");
                in = new Scanner(System.in);
                input = in.nextLine();
                options.setFilterValue(input);
            } else if ("between".equalsIgnoreCase(options.getFilterCriteria())) {
                printToConsole("Please enter the start value for the range");
                in = new Scanner(System.in);
                input = in.nextLine();
                options.setFilterStartRange(input);

                printToConsole("Please enter the end value for the range");
                in = new Scanner(System.in);
                input = in.nextLine();
                options.setFilterEndRange(input);
            }
        }
    }

}
