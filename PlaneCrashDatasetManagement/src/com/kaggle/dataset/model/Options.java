package com.kaggle.dataset.model;

import java.util.Arrays;

/**
 * The options class
 */
public class Options {

    private String inputFileLocation;
    private String inputFile;

    private String fieldOption;
    private String[] fieldsToShow;
    private Integer startRange;
    private Integer endRange;

    private String sortBy;
    private String sortOrder;

    private String searchField;
    private String searchCriteria;

    private String filterField;
    private String filterCriteria;
    private String filterValue;
    private String filterStartRange;
    private String filterEndRange;

    private boolean exportAsCSV;
    private String exportFileName;

    public String getInputFileLocation() {
        return inputFileLocation;
    }

    public void setInputFileLocation(String inputFileLocation) {
        this.inputFileLocation = inputFileLocation;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getFieldOption() {
        return fieldOption;
    }

    public void setFieldOption(String fieldOption) {
        this.fieldOption = fieldOption;
    }

    public String[] getFieldsToShow() {
        return fieldsToShow;
    }

    public void setFieldsToShow(String[] fieldsToShow) {
        this.fieldsToShow = fieldsToShow;
    }

    public Integer getStartRange() {
        return startRange;
    }

    public void setStartRange(Integer startRange) {
        this.startRange = startRange;
    }

    public Integer getEndRange() {
        return endRange;
    }

    public void setEndRange(Integer endRange) {
        this.endRange = endRange;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public String getFilterField() {
        return filterField;
    }

    public void setFilterField(String filterField) {
        this.filterField = filterField;
    }

    public String getFilterCriteria() {
        return filterCriteria;
    }

    public void setFilterCriteria(String filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    public String getFilterStartRange() {
        return filterStartRange;
    }

    public void setFilterStartRange(String filterStartRange) {
        this.filterStartRange = filterStartRange;
    }

    public String getFilterEndRange() {
        return filterEndRange;
    }

    public void setFilterEndRange(String filterEndRange) {
        this.filterEndRange = filterEndRange;
    }

    public boolean isExportAsCSV() {
        return exportAsCSV;
    }

    public void setExportAsCSV(boolean exportAsCSV) {
        this.exportAsCSV = exportAsCSV;
    }

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }

    @Override
    public String toString() {
        return "Options{" +
                "inputFileLocation='" + inputFileLocation + '\'' +
                ", inputFile='" + inputFile + '\'' +
                ", fieldOption='" + fieldOption + '\'' +
                ", fieldsToShow=" + Arrays.toString(fieldsToShow) +
                ", startRange=" + startRange +
                ", endRange=" + endRange +
                ", sortBy='" + sortBy + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", searchField='" + searchField + '\'' +
                ", searchCriteria='" + searchCriteria + '\'' +
                ", filterField='" + filterField + '\'' +
                ", filterCriteria='" + filterCriteria + '\'' +
                ", filterValue='" + filterValue + '\'' +
                ", filterStartRange='" + filterStartRange + '\'' +
                ", filterEndRange='" + filterEndRange + '\'' +
                ", exportAsCSV=" + exportAsCSV +
                ", exportFileName='" + exportFileName + '\'' +
                '}';
    }
}
