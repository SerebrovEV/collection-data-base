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
    private final String where = "where";

    private ArrayList<Map<String, Object>> dataBase;

    private String clearWord(String word) {
        return word.substring(word.indexOf("=") + 1).replaceAll("'| ", "");
    }

    public List<Map<String, Object>> execute(String request) throws Exception {

        String[] question = request.split(",");
        String command = question[0].toLowerCase();
        if (command.startsWith("insert")) {
            System.out.println(Arrays.toString(question));
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

    /**
     * Command Insert
     *
     * @param
     * @return
     */
    private List<Map<String, Object>> addRow(String[] sentence) {
        Map<String, Object> newRow = new HashMap<>();

        //   System.out.println(Arrays.toString(sentence));

        for (String str : sentence) {
            if (str.toLowerCase().contains(id)) {
                System.out.println(str);
                newRow.put(id, Long.valueOf(clearWord(str)));
                continue;
            }
            if (str.toLowerCase().contains(lastName)) {
                System.out.println(str);
                newRow.put("lastName", clearWord(str));
                continue;
            }
            if (str.toLowerCase().contains(age)) {
                newRow.put(age, Long.valueOf(clearWord(str)));
                System.out.println(str);
                continue;
            }
            if (str.toLowerCase().contains(cost)) {
                newRow.put(cost, Double.valueOf(clearWord(str)));
                System.out.println(str);
                continue;
            }
            if (str.toLowerCase().contains(active)) {
                newRow.put(active, Boolean.valueOf(clearWord(str)));
                System.out.println(str);
            } else throw new IllegalArgumentException();
        }

        return List.of(newRow);
    }

    private List<Map<String, Object>> updateRow(String[] sentence) {
        System.out.println(Arrays.toString(sentence));
        return dataBase;
    }

    private List<Map<String, Object>> selectRow(String[] sentence) {
        return dataBase;
    }

    private List<Map<String, Object>> deleteRow(String[] sentence) {
        Map<String, Object> newRow = new HashMap<>();
        for (String word : sentence) {
            if (word.toLowerCase().startsWith(where)) {

            }
        }
        return List.of(newRow);
    }

    private String getSymbol (String )

}
//.
//Колонка id имеет тип Long, колонка lastName имеет тип String,
//колонка cost имеет тип Double, колонка age имеет тип Long, колонка
//active имеет тип Boolean