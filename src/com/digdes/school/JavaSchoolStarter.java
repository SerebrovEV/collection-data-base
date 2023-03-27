package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;


public class JavaSchoolStarter {

    public JavaSchoolStarter() {
    }

    private final ArrayList<Map<String, Object>> dataBase = new ArrayList<>();


    public List<Map<String, Object>> execute(String request) throws Exception {

        if (request.toLowerCase().startsWith(Constant.INSERT)) {
            return addRow(request);
        } else if (request.toLowerCase().startsWith(Constant.UPDATE)) {
            return updateRow(request);
        } else if (request.toLowerCase().startsWith(Constant.DELETE)) {
            return deleteRow(request);
        } else if (request.toLowerCase().startsWith(Constant.SELECT)) {
            return selectRow(request);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Command Insert
     *
     * @param
     * @return
     */
    private List<Map<String, Object>> addRow(String sentence) {
        Map<String, Object> newRow = new HashMap<>();
        newRow = addInformationToMap(sentence, newRow);
        dataBase.add(newRow);
        return List.of(newRow);
    }

    /**
     * Command Update (в работе)
     *
     * @param sentence
     * @return
     */
    private List<Map<String, Object>> updateRow(String sentence) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (sentence.toLowerCase().contains("where")) {

            String[] request = sentence.split("where");

            if (request[1].toLowerCase().contains(Constant.AND)) {

                String[] criterion = paramOperation(request[1].toLowerCase().split(Constant.AND)[0]);
                String[] criterion2 = paramOperation(request[1].toLowerCase().split(Constant.AND)[1]);

                for (int i = 0; i < dataBase.size(); i++) {
                    if (filter(criterion, dataBase.get(i)) && filter(criterion2, dataBase.get(i))) {
                        dataBase.set(i, addInformationToMap(request[0], dataBase.get(i)));
                        result.add(dataBase.get(i));
                    }
                }
            } else if (request[1].toLowerCase().contains(Constant.OR)) {

                String[] criterion = paramOperation(request[1].toLowerCase().split(Constant.OR)[0]);
                String[] criterion2 = paramOperation(request[1].toLowerCase().split(Constant.OR)[1]);

                for (int i = 0; i < dataBase.size(); i++) {
                    if (filter(criterion, dataBase.get(i)) || filter(criterion2, dataBase.get(i))) {
                        dataBase.set(i, addInformationToMap(request[0], dataBase.get(i)));
                        result.add(dataBase.get(i));
                    }
                }
            } else {
                String[] criterion = paramOperation(request[1]);
                for (int i = 0; i < dataBase.size(); i++) {
                    System.out.println(Arrays.toString(criterion));
                    if (filter(criterion, dataBase.get(i))) {
                        dataBase.set(i, addInformationToMap(request[0], dataBase.get(i)));
                        result.add(dataBase.get(i));
                    }
                }
            }
        } else {
            for (int i = 0; i < dataBase.size(); i++) {
                dataBase.set(i, addInformationToMap(sentence, dataBase.get(i)));
                result.add(dataBase.get(i));
            }
        }

        return result;
    }

    /**
     * Command Select
     *
     * @param sentence
     * @return
     */
    private List<Map<String, Object>> selectRow(String sentence) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (sentence.toLowerCase().contains("where")) {

            String[] allCriterion = sentence.split("where");

            if (allCriterion[1].toLowerCase().contains(Constant.AND)) {
                String[] criterion = paramOperation(allCriterion[1].toLowerCase().split(Constant.AND)[0]);
                String[] criterion2 = paramOperation(allCriterion[1].toLowerCase().split(Constant.AND)[1]);

                for (Map<String, Object> next : dataBase) {
                    if (filter(criterion, next) && filter(criterion2, next)) {
                        result.add(next);
                    }
                }
            } else if (allCriterion[1].toLowerCase().contains(Constant.OR)) {
                String[] criterion = paramOperation(allCriterion[1].toLowerCase().split(Constant.OR)[0]);
                String[] criterion2 = paramOperation(allCriterion[1].toLowerCase().split(Constant.OR)[1]);

                for (Map<String, Object> next : dataBase) {
                    if (filter(criterion, next) || filter(criterion2, next)) {
                        result.add(next);
                    }
                }
            } else {
                String[] criterion = paramOperation(allCriterion[1]);

                for (Map<String, Object> next : dataBase) {
                    if (filter(criterion, next)) {
                        result.add(next);
                    }
                }
            }
        } else {
            result.addAll(dataBase);
        }
        return result;
    }

    /**
     * Command Delete
     *
     * @param sentence
     * @return
     */
    private List<Map<String, Object>> deleteRow(String sentence) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (sentence.toLowerCase().contains("where ")) {

            String[] allCriterion = sentence.split("where");

            if (allCriterion[1].toLowerCase().contains(Constant.AND)) {
                String[] criterion = paramOperation(allCriterion[1].toLowerCase().split(Constant.AND)[0]);
                String[] criterion2 = paramOperation(allCriterion[1].toLowerCase().split(Constant.AND)[1]);
                Iterator<Map<String, Object>> dataBAseIterator = dataBase.iterator();

                while (dataBAseIterator.hasNext()) {
                    Map<String, Object> next = dataBAseIterator.next();
                    if (filter(criterion, next) && filter(criterion2, next)) {
                        result.add(next);
                        dataBAseIterator.remove();
                    }
                }
            } else if (allCriterion[1].toLowerCase().contains(Constant.OR)) {
                String[] criterion = paramOperation(allCriterion[1].toLowerCase().split(Constant.OR)[0]);
                String[] criterion2 = paramOperation(allCriterion[1].toLowerCase().split(Constant.OR)[1]);
                Iterator<Map<String, Object>> dataBAseIterator = dataBase.iterator();

                while (dataBAseIterator.hasNext()) {
                    Map<String, Object> next = dataBAseIterator.next();

                    if (filter(criterion, next) || filter(criterion2, next)) {
                        result.add(next);
                        dataBAseIterator.remove();
                    }
                }
            } else {
                String[] criterion = paramOperation(allCriterion[1]);
                Iterator<Map<String, Object>> dataBAseIterator = dataBase.iterator();

                while (dataBAseIterator.hasNext()) {
                    Map<String, Object> next = dataBAseIterator.next();

                    if (filter(criterion, next)) {
                        result.add(next);
                        dataBAseIterator.remove();
                    }
                }
            }
        } else if (!(sentence.toLowerCase().contains("where"))) {
            Iterator<Map<String, Object>> dataBaseIterator = dataBase.iterator();
            while (dataBaseIterator.hasNext()) {
                Map<String, Object> next = dataBaseIterator.next();
                result.add(next);
                dataBaseIterator.remove();
            }
        } else {
            throw new RuntimeException("Некорректная команда на удаление с использование WHERE");
        }
        return result;
    }

    private String clearWord(String word) {
        return word.substring(word.indexOf("=") + 1).replaceAll("'| ", "");
    }

    private Map<String, Object> addInformationToMap(String informationToAdd, Map<String, Object> row) {
        for (String str : informationToAdd.split(",")) {
            if (str.toLowerCase().contains(Constant.ID)) {
                row.put(Constant.ID, Long.valueOf(clearWord(str)));
                continue;
            }
            if (str.toLowerCase().contains(Constant.LAST_NAME)) {
                row.put(Constant.LAST_NAME, clearWord(str));
                continue;
            }
            if (str.toLowerCase().contains(Constant.AGE)) {
                row.put(Constant.AGE, Long.valueOf(clearWord(str)));
                continue;
            }
            if (str.toLowerCase().contains(Constant.COST)) {
                row.put(Constant.COST, Double.valueOf(clearWord(str)));
                continue;
            }
            if (str.toLowerCase().contains(Constant.ACTIVE)) {
                row.put(Constant.ACTIVE, Boolean.valueOf(clearWord(str)));
            } else throw new RuntimeException("Некорректный запрос");
        }
        return row;
    }

    private String[] paramOperation(String operation) {
        String[] data = new String[3];
        Matcher matcher = Constant.OPERATION.matcher(operation.replaceAll("'| ", ""));
        if (matcher.find()) {
            for (int i = 0; i < 3; i++) {
                data[i] = matcher.group(i + 1);
            }
            data[0]= data[0].toLowerCase();
        } else {
            throw new RuntimeException("Некорретный запрос");
        }
        return data;
    }

    /**
     * Filter logic command
     *
     * @param criterion
     * @param maps
     * @return
     */
    private boolean filter(String[] criterion, Map<String, Object> maps) {
        switch (criterion[1]) {
            case "=" -> {
                return isEquals(criterion, maps);
            }

            case "!=" -> {
                return isNotEquals(criterion, maps);
            }

            case "like" -> {
                return like(criterion, maps);
            }
            case "ilike" -> {
                return iLike(criterion, maps);
            }
            case ">=" -> {
                return greaterThanOrEqual(criterion, maps);
            }
            case "<=" -> {
                return lessThanOrEqual(criterion, maps);
            }
            case "<" -> {
                return less(criterion, maps);
            }
            case ">" -> {
                return more(criterion, maps);
            }
            default -> throw new RuntimeException("Некоректный запрос");
        }
    }

    private static boolean isEquals(String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Long.valueOf(operation[2]).equals(Long.valueOf(map.get(operation[0]).toString()));
            }
            case Constant.LAST_NAME -> {
                if (map.get(operation[0]) == null) {
                    return false;
                } else {
                    return operation[2].equals(map.get(operation[0]).toString());
                }
            }
            case Constant.COST -> {
                return Double.valueOf(operation[2]).equals(Double.valueOf(map.get(operation[0]).toString()));
            }
            case Constant.ACTIVE -> {
                return Boolean.valueOf(operation[2]).equals(Boolean.valueOf(map.get(operation[0]).toString()));
            }
            default -> throw new RuntimeException("Некоректный запрос");

        }
    }

    private static boolean isNotEquals(String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return !(Long.valueOf(operation[2]).equals(Long.valueOf(map.get(operation[0]).toString())));
            }
            case Constant.LAST_NAME -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return !(operation[2].equals(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return !(Double.valueOf(operation[2]).equals(Double.valueOf(map.get(operation[0]).toString())));
            }
            case Constant.ACTIVE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return !(Boolean.valueOf(operation[2]).equals(Boolean.valueOf(map.get(operation[0]).toString())));
            }
            default -> throw new RuntimeException("Некоректный запрос");
        }
    }

    private static boolean like(String[] operation, Map<String, Object> map) {
        if (operation[0].equals(Constant.LAST_NAME)) {
            if (map.get(operation[0]) == null) {
                return false;
            }
            return operation[2].equals(map.get(operation[0]).toString());
        } else {
            throw new RuntimeException("Некоректный запрос");
        }
    }

    private static boolean iLike(String[] operation, Map<String, Object> map) {
        if (operation[0].equals(Constant.LAST_NAME)) {
            if (map.get(operation[0]) == null) {
                return false;
            }
            return operation[2].equalsIgnoreCase(map.get(operation[0]).toString());
        } else {
            throw new RuntimeException("Некоректный запрос");
        }
    }

    private static boolean greaterThanOrEqual(String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Long.parseLong(operation[2]) <= (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Double.parseDouble(operation[2]) <= Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new RuntimeException("Некоректный запрос");
        }
    }

    private static boolean lessThanOrEqual(String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Long.parseLong(operation[2]) >= (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Double.parseDouble(operation[2]) >= Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new RuntimeException("Некоректный запрос");
        }
    }

    private static boolean less(String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                return Long.parseLong(operation[2]) > (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Double.parseDouble(operation[2]) > Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new RuntimeException("Некоректный запрос");
        }
    }

    private static boolean more(String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Long.parseLong(operation[2]) < (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Double.parseDouble(operation[2]) < Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new RuntimeException("Некоректный запрос");
        }
    }
}
