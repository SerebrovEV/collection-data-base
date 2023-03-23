package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;


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
            deleteRow(request);
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
        //   System.out.println(Arrays.toString(sentence));
        for (String str : sentence.split(",")) {
            if (str.toLowerCase().contains(Constant.ID)) {
                System.out.println(str);
                newRow.put(Constant.ID, Long.valueOf(clearWord(str)));
                continue;
            }
            if (str.toLowerCase().contains(Constant.LAST_NAME)) {
                System.out.println(str);
                newRow.put("lastName", clearWord(str));
                continue;
            }
            if (str.toLowerCase().contains(Constant.AGE)) {
                newRow.put(Constant.AGE, Long.valueOf(clearWord(str)));
                System.out.println(str);
                continue;
            }
            if (str.toLowerCase().contains(Constant.COST)) {
                newRow.put(Constant.COST, Double.valueOf(clearWord(str)));
                System.out.println(str);
                continue;
            }
            if (str.toLowerCase().contains(Constant.ACTIVE)) {
                newRow.put(Constant.ACTIVE, Boolean.valueOf(clearWord(str)));
                System.out.println(str);
            } else throw new IllegalArgumentException();
        }
        dataBase.add(newRow);
        return List.of(newRow);
    }

    private List<Map<String, Object>> updateRow(String sentence) {
        System.out.println(sentence);
        return dataBase;
    }

    private List<Map<String, Object>> selectRow(String sentence) {
        return dataBase;
    }

    private List<Map<String, Object>> deleteRow(String sentence) {
        Map<String, Object> newRow = new HashMap<>();
        List<Map<String, Object>> newList = new ArrayList<>();
        String[] str = sentence.split("where");
        paramOperation(str[1]);
        String[] param = Constant.AND.split(str[1]);
        for (String par : param) {
            String[] dataOperation = paramOperation(par);
            newList = dataBase.stream().filter(s -> filter(dataOperation, s)).toList();
            System.out.println(newList);
        }
        return newList;
    }

    private String clearWord(String word) {
        return word.substring(word.indexOf("=") + 1).replaceAll("'| ", "");
    }

    private String[] paramOperation(String operation) {
        System.out.println(operation.replace(" ", ""));
        String[] data = new String[3];
        Matcher matcher = Constant.OPERATION.matcher(operation.replace(" ", ""));
        if (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            for (int i = 0; i < 3; i++) {
                data[i] = matcher.group(i);
            }
        }
        return data;
    }

    private boolean filter(String[] strs, Map<String, Object> maps) {
        boolean result = false;
        switch (strs[2]) {
            case "=" -> {
                result = strs[2] == maps.get(strs[1]);
            }

            case "!=" -> {
                result = strs[2] != maps.get(strs[1]);
            }

            case "like" -> {
                result = strs[2].equals(maps.get(strs[1]).toString());
            }
            case "ilike" -> {
                result = strs[2].equalsIgnoreCase(maps.get(strs[1]).toString());
            }
            case ">=" -> {
                if (strs[1].equals(Constant.ID) || strs[1].equals(Constant.AGE) || strs[1].equals(Constant.COST)) {
                    result = Double.parseDouble(strs[2]) >= (Double) maps.get(strs[1]);

                }
            }
            case "<=" -> {
                if (strs[1].equals(Constant.ID) || strs[1].equals(Constant.AGE) || strs[1].equals(Constant.COST)) {
                    result = Double.parseDouble(strs[2]) >= (Double) maps.get(strs[1]);
                }
            }
            case "<" -> {
                if (strs[1].equals(Constant.ID) || strs[1].equals(Constant.AGE) || strs[1].equals(Constant.COST)) {
                    result = Double.parseDouble(strs[2]) >= (Double) maps.get(strs[1]);
                }
            }
            case ">" -> {
                if (strs[1].equals(Constant.ID) || strs[1].equals(Constant.AGE) || strs[1].equals(Constant.COST)) {
                    result = Double.parseDouble(strs[2]) >= (Double) maps.get(strs[1]);
                }
            }
        }
        return result;
    }

}
//.
//Колонка id имеет тип Long, колонка lastName имеет тип String,
//колонка cost имеет тип Double, колонка age имеет тип Long, колонка
//active имеет тип Boolean