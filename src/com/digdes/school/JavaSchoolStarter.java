package com.digdes.school;

import java.util.*;

public class JavaSchoolStarter {
    public JavaSchoolStarter() {
    }

    //Map = id lastName age cost active

    private final String id = "id";
    private final String lastName = "lastname";
    private final String age = "age";
    private final String cost = "cost";
    private final String active = "active";

    private ArrayList<Map<String, Object>> dataBase;

    public List<Map<String, Object>> execute(String request) throws Exception {

        String[] question = request.split(",");
        String command = question[0].toLowerCase();
        if (command.startsWith("insert")) {
            List<Map<String, Object>> newList = addRow(question);
            return newList;
        } else if (command.startsWith("update")) {
            System.out.println(Arrays.toString(question));
        } else if (command.startsWith("delete")) {
            System.out.println(Arrays.toString(question));
        } else if (command.startsWith("select")) {
            System.out.println(Arrays.toString(question));
        } else {
            throw new IllegalArgumentException();
        }
        return new ArrayList<>();
    }

    private ArrayList<Map<String, Object>> addRow(String[] strs) {
        Map<String, Object> newRow = new HashMap<>();
        System.out.println(Arrays.toString(strs));
        for (String str : strs) {
            if (str.toLowerCase().contains(id)) {
                newRow.put(id, Long.valueOf(str.substring(str.charAt('=') + 1)));
                continue;
            }
//            if (str.toLowerCase().contains(lastName)) {
//                newRow.put("lastName", (str.substring(str.charAt('=') + 1)));
//                continue;
//            }
            if (str.toLowerCase().contains(age)) {
                newRow.put(age, Long.valueOf(str.substring(str.charAt('=') + 1)));
                continue;
            }
            if (str.toLowerCase().contains(cost)) {
                newRow.put(cost, Double.valueOf(str.substring(str.charAt('=') + 1)));
                continue;
            }
            if (str.toLowerCase().contains(active)) {
                newRow.put(active, Boolean.valueOf(str.substring(str.charAt('=') + 1)));
            }
        }

        return new ArrayList<>();
    }


}
//.
//Колонка id имеет тип Long, колонка lastName имеет тип String,
//колонка cost имеет тип Double, колонка age имеет тип Long, колонка
//active имеет тип Boolean