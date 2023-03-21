package com.digdes.school;


import java.util.List;

public class Main {

    public static void main(String[] args) {

        JavaSchoolStarter starter = new JavaSchoolStarter();
        List<String> commands = List.of(
                "INSERT VALUES 'lastName' = 'Федоров' , 'id'=3, 'age'=40, 'active'=true",
                "UPDATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3",
                "UPdATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3",
                "Delete VALUES 'active'=false, 'cost'=10.1 where 'id'=3",
                "Select VALUES 'active'=false, 'cost'=10.1 where 'id'=3");
        try {

            for (String str : commands) {
                starter.execute(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("Done");
        }

    }
}
