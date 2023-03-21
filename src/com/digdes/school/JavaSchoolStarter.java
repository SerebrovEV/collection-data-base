package com.digdes.school;

import java.util.*;

public class JavaSchoolStarter {
    public JavaSchoolStarter() {
    }
    private ArrayList<Map<String, Object>> dataBase;

    public List<Map<String, Object>> execute(String request) throws Exception {

        String[] question = request.split(",");
        String command = question[0].toLowerCase();
        if (command.startsWith("insert")) {
            System.out.println(Arrays.toString(question));
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



        return new ArrayList<>();
    }


}
