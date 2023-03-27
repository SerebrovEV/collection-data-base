package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;


public class JavaSchoolStarter {

    public JavaSchoolStarter() {
    }

    private final ArrayList<Map<String, Object>> dataBase = new ArrayList<>();


    public List<Map<String, Object>> execute(String request) throws Exception {

        if (request.toLowerCase().startsWith(Constant.INSERT)) {
            System.out.println(request);
            return addRow(request);
        } else if (request.toLowerCase().startsWith(Constant.UPDATE)) {
            System.out.println(request);
        } else if (request.toLowerCase().startsWith(Constant.DELETE)) {
            System.out.println(request);
            return deleteRow(request);
        } else if (request.toLowerCase().startsWith(Constant.SELECT)) {
            System.out.println(request);
        } else {
            throw new IllegalArgumentException();
        }
        return new ArrayList<>();
    }

    /**
     * Command Insert
     *
     * @param
     * @return
     */
    private List<Map<String, Object>> addRow(String sentence) {
        Map<String, Object> newRow = new HashMap<>();
        for (String str : sentence.split(",")) {
            if (str.toLowerCase().contains(Constant.ID)) {
            //    System.out.println(str);
                newRow.put(Constant.ID, Long.valueOf(clearWord(str)));
                continue;
            }
            if (str.toLowerCase().contains(Constant.LAST_NAME)) {
             //   System.out.println(str);
                newRow.put("lastName", clearWord(str));
                continue;
            }
            if (str.toLowerCase().contains(Constant.AGE)) {
                newRow.put(Constant.AGE, Long.valueOf(clearWord(str)));
             //   System.out.println(str);
                continue;
            }
            if (str.toLowerCase().contains(Constant.COST)) {
                newRow.put(Constant.COST, Double.valueOf(clearWord(str)));
             //   System.out.println(str);
                continue;
            }
            if (str.toLowerCase().contains(Constant.ACTIVE)) {
                newRow.put(Constant.ACTIVE, Boolean.valueOf(clearWord(str)));
            //    System.out.println(str);
            } else throw new IllegalArgumentException();
        }
        dataBase.add(newRow);
        return List.of(newRow);
    }

    private List<Map<String, Object>> updateRow(String sentence) {
        System.out.println(sentence);
        List<Map<String, Object>> result = new ArrayList<>();
        return result;
    }

    private List<Map<String, Object>> selectRow(String sentence) {
        List<Map<String, Object>> result = new ArrayList<>();
        return result;
    }

    private List<Map<String, Object>> deleteRow(String sentence) {
        List<Map<String, Object>> result = new ArrayList<>();
        String[] str = sentence.split("where");
        System.out.println(Arrays.toString(str));
        String[] criterion = paramOperation(str[1]);
        System.out.println(Arrays.toString(criterion));
        Iterator<Map<String, Object>> dataBAseIterator = dataBase.iterator();
        while (dataBAseIterator.hasNext()) {
            Map<String, Object> next = dataBAseIterator.next();
            if (filter(criterion, next)) {
                result.add(next);
                dataBAseIterator.remove();
            }
        }
        return result;
    }

    private String clearWord(String word) {
        return word.substring(word.indexOf("=") + 1).replaceAll("'| ", "");
    }

    private String[] paramOperation(String operation) {
        String[] data = new String[3];
        Matcher matcher = Constant.OPERATION.matcher(operation.replace(" ", ""));
        if (matcher.find()) {
            for (int i = 0; i < 3; i++) {
                data[i] = matcher.group(i+1);
                System.out.println(data[i]);
            }
        } else {
            throw new RuntimeException();
        }
        return data;
    }

    private boolean filter(String[] strs, Map<String, Object> maps) {
        switch (strs[1]) {
            case "=" -> {
                System.out.println(Arrays.toString(strs));
                return isEquals(strs, maps);
            }

            case "!=" -> {
                return isNotEquals(strs, maps);
            }

            case "like" -> {
                return like(strs, maps);
            }
            case "ilike" -> {
                return iLike(strs, maps);
            }
            case ">=" -> {
                return greaterThanOrEqual(strs, maps);
            }
            case "<=" -> {
                return lessThanOrEqual(strs, maps);
            }
            case "<" -> {
               return less(strs, maps);
            }
            case ">" -> {
              return more(strs, maps);
            }
            default -> throw new RuntimeException();
        }
    }

    private static boolean isEquals(String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                return Long.valueOf(operation[2]).equals(Long.valueOf(map.get(operation[0]).toString()));
            }
            case Constant.LAST_NAME -> {
                return operation[2].equals(map.get(operation[0]).toString());
            }
            case Constant.COST -> {
                return Double.valueOf(operation[2]).equals(Double.valueOf(map.get(operation[0]).toString()));
            }
            case Constant.ACTIVE -> {
                return Boolean.valueOf(operation[2]).equals(Boolean.valueOf(map.get(operation[0]).toString()));
            }
            default -> throw new RuntimeException();

        }
    }

    private static boolean isNotEquals(String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                return !(Long.valueOf(operation[2]).equals(Long.valueOf(map.get(operation[0]).toString())));
            }
            case Constant.LAST_NAME -> {
                return !(operation[2].equals(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                return !(Double.valueOf(operation[2]).equals(Double.valueOf(map.get(operation[0]).toString())));
            }
            case Constant.ACTIVE -> {
                return !(Boolean.valueOf(operation[2]).equals(Boolean.valueOf(map.get(operation[0]).toString())));
            }
            default -> throw new RuntimeException();
        }
    }
    private static boolean like (String[] operation, Map<String, Object> map) {
        if (operation[0].equals(Constant.LAST_NAME)) {
            return operation[2].equals(map.get(operation[0]).toString());
        } else {
            throw new RuntimeException();
        }
    }

    private static boolean iLike (String[] operation, Map<String, Object> map) {
        if (operation[0].equals(Constant.LAST_NAME)) {
            return operation[2].equalsIgnoreCase(map.get(operation[0]).toString());
        } else {
            throw new RuntimeException();
        }
    }
    private static boolean greaterThanOrEqual (String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                return Long.parseLong(operation[2]) >= (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                return Double.parseDouble(operation[2]) >= Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new RuntimeException();
        }
    }

    private static boolean lessThanOrEqual (String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                return Long.parseLong(operation[2]) <= (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                return Double.parseDouble(operation[2]) <= Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new RuntimeException();
        }
    }
    private static boolean less (String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                return Long.parseLong(operation[2]) < (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                return Double.parseDouble(operation[2]) < Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new RuntimeException();
        }
    }
    private static boolean more (String[] operation, Map<String, Object> map) {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                return Long.parseLong(operation[2]) > (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                return Double.parseDouble(operation[2]) > Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new RuntimeException();
        }
    }
}
