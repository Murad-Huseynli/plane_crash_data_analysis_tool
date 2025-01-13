package com.kaggle.dataset.util;

import com.kaggle.dataset.model.Options;
import com.kaggle.dataset.model.PlaneCrashData;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;
import java.util.function.Predicate;

import static com.kaggle.dataset.util.PrintUtil.printToConsole;

/**
 * The search & filter util class
 */
public class SearchAndFilterUtil {

    /**
     * Method to get search predicate
     *
     * @param options
     * @param headerFieldMap
     * @return The predicate based on the options
     */
    public static Predicate<PlaneCrashData> getSearchPredicate(Options options, Map<String, String> headerFieldMap) {
        String searchField = options.getSearchField();
        if (searchField != null) {
            String searchCriteria = options.getSearchCriteria();
            return (planeCrashData) -> {
                try {
                    Field field = PlaneCrashData.class.getDeclaredField(headerFieldMap.get(searchField));
                    field.setAccessible(true);
                    if (field.getType().equals(LocalDate.class)) {
                        LocalDate field1 = (LocalDate) field.get(planeCrashData);
                        LocalDate field2 = LocalDate.parse(searchCriteria, new DateTimeFormatterBuilder()
                                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter());
                        return field1.equals(field2);
                    } else if (field.getType().equals(LocalTime.class)) {
                        LocalTime field1 = (LocalTime) field.get(planeCrashData);
                        LocalTime field2 = LocalTime.parse(searchCriteria, new DateTimeFormatterBuilder()
                                .appendOptional(DateTimeFormatter.ofPattern("HH:mm")).toFormatter());
                        return field2.equals(field1);
                    } else if (field.getType().equals(Integer.class)) {
                        Integer field1 = (Integer) field.get(planeCrashData);
                        Integer field2 = Integer.parseInt(searchCriteria);
                        return field1.equals(field2);
                    } else if (field.getType().equals(Double.class)) {
                        Double field1 = (Double) field.get(planeCrashData);
                        Double field2 = Double.parseDouble(searchCriteria);
                        return field1.equals(field2);
                    } else {
                        return ((String) field.get(planeCrashData)).toLowerCase().contains(searchCriteria.toLowerCase());
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    printToConsole(String.format("Error. %s", e.getMessage()));
                }
                return false;
            };
        }

        return planeCrashData -> true;
    }

    /**
     * Method to get filter predicate
     *
     * @param options
     * @param headerFieldMap
     * @return The filter predicate based on the options
     */
    public static Predicate<PlaneCrashData> getFilterPredicate(Options options, Map<String, String> headerFieldMap) {
        String filterField = options.getFilterField();
        if (filterField != null) {
            String filterCriteria = options.getFilterCriteria();
            String filterValue = options.getFilterValue();
            String filterStartRange = options.getFilterStartRange();
            String filterEndRange = options.getFilterEndRange();
            return (planeCrashData) -> {
                try {
                    Field field = PlaneCrashData.class.getDeclaredField(headerFieldMap.get(filterField));
                    field.setAccessible(true);
                    if (field.getType().equals(LocalDate.class)) {
                        if ("between".equalsIgnoreCase(filterCriteria)) {
                            LocalDate field1 = (LocalDate) field.get(planeCrashData);
                            LocalDate field2 = LocalDate.parse(filterStartRange, new DateTimeFormatterBuilder()
                                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter());
                            LocalDate field3 = LocalDate.parse(filterEndRange, new DateTimeFormatterBuilder()
                                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter());
                            return (field1.isAfter(field2) || field1.isEqual(field2))
                                    && (field1.isBefore(field3) || field1.isEqual(field3));
                        } else if ("is_null".equalsIgnoreCase(filterCriteria)) {
                            return field.get(planeCrashData) == null;
                        } else {
                            LocalDate field1 = (LocalDate) field.get(planeCrashData);
                            LocalDate field2 = LocalDate.parse(filterValue, new DateTimeFormatterBuilder()
                                    .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter());
                            if(field1 == null)
                                return false;
                            if ("equals".equalsIgnoreCase(filterCriteria)) {
                                return field1.equals(field2);
                            } else if ("greater_than".equalsIgnoreCase(filterCriteria)) {
                                return field1.isAfter(field2);
                            } else if ("less_than".equalsIgnoreCase(filterCriteria)) {
                                return field1.isBefore(field2);
                            } else if ("greater_than_equal".equalsIgnoreCase(filterCriteria)) {
                                return field1.isAfter(field2) || field1.isEqual(field2);
                            } else if ("less_than_equal".equalsIgnoreCase(filterCriteria)) {
                                return field1.isBefore(field2) || field1.isEqual(field2);
                            } else if ("specific_year".equalsIgnoreCase(filterCriteria)) {
                                return field1.getYear() == field2.getYear();
                            } else if ("specific_month".equalsIgnoreCase(filterCriteria)) {
                                return field1.getMonth() == field2.getMonth();
                            } else if ("specific_day".equalsIgnoreCase(filterCriteria)) {
                                return field1.getDayOfMonth() == field2.getDayOfMonth();
                            }
                        }
                    } else if (field.getType().equals(LocalTime.class)) {
                        if ("between".equalsIgnoreCase(filterCriteria)) {
                            LocalTime field1 = (LocalTime) field.get(planeCrashData);
                            LocalTime field2 = LocalTime.parse(filterStartRange, new DateTimeFormatterBuilder()
                                    .appendOptional(DateTimeFormatter.ofPattern("HH:mm")).toFormatter());
                            LocalTime field3 = LocalTime.parse(filterEndRange, new DateTimeFormatterBuilder()
                                    .appendOptional(DateTimeFormatter.ofPattern("HH:mm")).toFormatter());
                            return (field1.isAfter(field2) || field1.equals(field2))
                                    && (field1.isBefore(field3) || field1.equals(field3));
                        } else if ("is_null".equalsIgnoreCase(filterCriteria)) {
                            return field.get(planeCrashData) == null;
                        } else {
                            LocalTime field1 = (LocalTime) field.get(planeCrashData);
                            LocalTime field2 = LocalTime.parse(filterValue, new DateTimeFormatterBuilder()
                                    .appendOptional(DateTimeFormatter.ofPattern("HH:mm")).toFormatter());
                            if(field1 == null)
                                return false;
                            if ("equals".equalsIgnoreCase(filterCriteria)) {
                                return field1.equals(field2);
                            } else if ("greater_than".equalsIgnoreCase(filterCriteria)) {
                                return field1.isAfter(field2);
                            } else if ("less_than".equalsIgnoreCase(filterCriteria)) {
                                return field1.isBefore(field2);
                            } else if ("greater_than_equal".equalsIgnoreCase(filterCriteria)) {
                                return field1.isAfter(field2) || field1.equals(field2);
                            } else if ("less_than_equal".equalsIgnoreCase(filterCriteria)) {
                                return field1.isBefore(field2) || field1.equals(field2);
                            }
                        }
                    } else if (field.getType().equals(Integer.class)) {
                        if ("between".equalsIgnoreCase(filterCriteria)) {
                            Integer field1 = (Integer) field.get(planeCrashData);
                            Integer field2 = Integer.parseInt(filterStartRange);
                            Integer field3 = Integer.parseInt(filterEndRange);
                            return (field1 >= field2) && (field1 <= field3);
                        } else if ("is_null".equalsIgnoreCase(filterCriteria)) {
                            return field.get(planeCrashData) == null;
                        } else {
                            Integer field1 = (Integer) field.get(planeCrashData);
                            Integer field2 = Integer.parseInt(filterValue);
                            if ("equals".equalsIgnoreCase(filterCriteria)) {
                                return field1 == field2;
                            } else if ("greater_than".equalsIgnoreCase(filterCriteria)) {
                                return field1 > field2;
                            } else if ("less_than".equalsIgnoreCase(filterCriteria)) {
                                return field1 < field2;
                            } else if ("greater_than_equal".equalsIgnoreCase(filterCriteria)) {
                                return field1 >= field2;
                            } else if ("less_than_equal".equalsIgnoreCase(filterCriteria)) {
                                return field1 <= field2;
                            }
                        }
                    } else if (field.getType().equals(Double.class)) {
                        if ("between".equalsIgnoreCase(filterCriteria)) {
                            Double field1 = (Double) field.get(planeCrashData);
                            Double field2 = Double.parseDouble(filterStartRange);
                            Double field3 = Double.parseDouble(filterEndRange);
                            return (field1 >= field2) && (field1 <= field3);
                        } else if ("is_null".equalsIgnoreCase(filterCriteria)) {
                            return field.get(planeCrashData) == null;
                        } else {
                            Double field1 = (Double) field.get(planeCrashData);
                            Double field2 = Double.parseDouble(filterValue);
                            if ("equals".equalsIgnoreCase(filterCriteria)) {
                                return field1 == field2;
                            } else if ("greater_than".equalsIgnoreCase(filterCriteria)) {
                                return field1 > field2;
                            } else if ("less_than".equalsIgnoreCase(filterCriteria)) {
                                return field1 < field2;
                            } else if ("greater_than_equal".equalsIgnoreCase(filterCriteria)) {
                                return field1 >= field2;
                            } else if ("less_than_equal".equalsIgnoreCase(filterCriteria)) {
                                return field1 <= field2;
                            }
                        }
                    } else {
                        String field1 = (String) field.get(planeCrashData);
                        String field2 = filterValue;
                        if ("starts_with".equalsIgnoreCase(filterCriteria)) {
                            return field1.toLowerCase().startsWith(field2.toLowerCase());
                        } else if ("ends_with".equalsIgnoreCase(filterCriteria)) {
                            return field1.toLowerCase().endsWith(field2.toLowerCase());
                        } else if ("contains".equalsIgnoreCase(filterCriteria)) {
                            return field1.toLowerCase().contains(field2.toLowerCase());
                        } else if ("is_null".equalsIgnoreCase(filterCriteria)) {
                            return field1 == null || "".equals(field1);
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    printToConsole(String.format("Error. %s", e.getMessage()));
                }
                return false;
            };
        }
        return planeCrashData -> true;
    }
}
