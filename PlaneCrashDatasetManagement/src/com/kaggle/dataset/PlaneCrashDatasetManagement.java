package com.kaggle.dataset;

import com.kaggle.dataset.exception.InvalidCSVException;
import com.kaggle.dataset.helper.OptionsHelper;
import com.kaggle.dataset.model.Options;
import com.kaggle.dataset.model.PlaneCrashData;
import com.kaggle.dataset.util.CSVUtil;
import com.kaggle.dataset.util.PrintUtil;

import java.io.IOException;
import java.util.List;

import static com.kaggle.dataset.util.PrintUtil.printToConsole;

/**
 * The plane crash dataset management class
 */
public class PlaneCrashDatasetManagement {

    /**
     * Main method. Set the args for input file location and input file name
     *
     * @param args
     */
    public static void main(String[] args) {//please, specify the related path to the .csv file
        String fileLocation = "C:\\Users\\99455\\Desktop\\Team13_PROJECT\\PlaneCrashDatasetManagement\\"; 
        String inputFile = "Large_Passenger_Plane_Crashes_1933_to_2009.csv";
        if (args.length == 2) {
            fileLocation = args[0];
            inputFile = args[1];
        }

        printToConsole("*********************************************************************************************************");
        printToConsole("*********************************************************************************************************");
        printToConsole("**********************  Welcome to Plane Crash Dataset Management System  *******************************");
        printToConsole("*********************************************************************************************************");
        printToConsole("*********************************************************************************************************");

        Options options = OptionsHelper.collectInitialOptions();
        options.setInputFileLocation(fileLocation);
        options.setInputFile(inputFile);
        try {
            List<PlaneCrashData> planeCrashDataList = CSVUtil.readCSV(options.getInputFileLocation() + options.getInputFile(), true);
            PrintUtil.formatAndPrint(planeCrashDataList, options);

            while (true) {
                options = OptionsHelper.collectMoreOptions(options);
                if(options == null) {
                    options = OptionsHelper.collectInitialOptions();
                    options.setInputFileLocation(fileLocation);
                    options.setInputFile(inputFile);
                }
                PrintUtil.formatAndPrint(planeCrashDataList, options);
            }
        } catch (IOException | InvalidCSVException e) {
            printToConsole(String.format("Error while parsing the CSV. %s", e.getMessage()));
        }
    }


}
