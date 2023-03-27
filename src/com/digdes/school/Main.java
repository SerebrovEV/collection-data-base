package com.digdes.school;


import java.util.List;

public class Main {

    public static void main(String[] args) {

        JavaSchoolStarter starter = new JavaSchoolStarter();
        List<String> commands = List.of(
                "INSERT VALUES 'lastName' = ' Test1' , 'id'= 1, 'age'= 42, 'active'= true, 'cost'=4.5",
                "INSERT VALUES 'lastName' = ' Test2' , 'id'= 2, 'age'= 43, 'active'= true, 'cost'=4.7",
                "INSERT VALUES 'lastName' = ' Test3' , 'id'= 3, 'age'= 40, 'active'= true, 'cost'=4.8",
                "INSERT VALUES 'lastName' = ' Test4' , 'id'= 4, 'age'= 41, 'active'= false",
                "INSERT VALUES 'id'= 5, 'age'= 10, 'active'= true",
                "INSERT VALUES 'lastName' = ' Test6' , 'id'= 6, 'age'= 48, 'cost'=4.8",
                "INSERT VALUES 'lastName' = ' Test7' , 'id'= 7, 'age'= 41, 'active'= true",
                "INSERT VALUES 'lastName' = ' Test8' , 'id'= 8, 'age'= 43, 'active'= true",
                //  "UPDATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3",
                //   "UPdATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3",
               // "Delete where 'id' = 4",
                "update values 'lastName' = 'Test16' where 'LastName' = ' Test2'",
                "select"
                // "Select where 'id'=3"
        );
        try {

            for (String str : commands) {
                System.out.println(starter.execute(str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
