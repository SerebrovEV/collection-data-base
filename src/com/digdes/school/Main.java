package com.digdes.school;


import java.util.List;

public class Main {

    public static void main(String... args) {

        JavaSchoolStarter starter = new JavaSchoolStarter();

        try {
            for (String str : commands) {
                System.out.println(starter.execute(str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Пример запроса разных команд на вход обработчика команд
     */
    private static List<String> commands = List.of(
            "INSERT VALUES 'lastName' = 'Test1' , 'age'= 40, 'active'= true, 'cost'=4.5",
            "INSERT VALUES 'lastName' = 'Test2' , 'id'= 2, 'age'= 41, 'active'= false, 'cost'=3.5",
            "INSERT VALUES 'lastName' = 'Test3' , 'id'= 3, 'age'= 42, 'active'= true, 'cost'=4.5",
            "INSERT values 'lastName' = 'Test4' , 'id'= 4, 'age'= 43, 'active'= false, 'cost'=10",
            "INSERT VALUES 'lastName' = 'Test5' , 'id'= 5, 'age'= 45",
            "insert VALUES 'id'= 6,  'active'= true, 'cost'=4.5",
            "INSERT VALUES 'lastName' = 'Test7' , 'id'= 7, 'age'= 42, 'active'= true, 'cost'=7.0",
            "INSERT VALUES 'lastName' = 'est8' , 'id'= 8, 'age'= 43, 'active'= false",
            "INSERT VALUES 'lastName' = 'test9' , 'id'= 9, 'age'= 40, 'active'= true, 'cost'=5.8",
            "INSERT VALUES 'lastName' = 'ADtest10' , 'id'= 10, 'age'= 31, 'active'= false",
            "INSERT VALUES 'id'= 11, 'active'= true",
            "INSERT VALUES 'lastName' = 'енTest' , 'id'= 12, 'age'= 48, 'cost'=1.8",
            "INsERT VALUES 'lastName' = 'TestT12' , 'id'= 13, 'age'= 41, 'active'= true",
            "INSERT VALUES 'lastName' = 'tEst13' , 'id'= 14, 'active'= true",
            "UPDATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3",
            "UPdATE VALUES 'active'=false, 'cost'=10.1 where 'age' >= 48",
            "UPdATE VALUES 'cost'=null",
            "select where 'id' != 0",
            "update values 'lastName' = 'Test16' where 'LastName' = ' Test2'",
            "select",
            "select where 'lastName' ilike '%Test'",
            "select where 'lastName' like 'Test%'",
            "select where 'lastName' like '%test%'",
            "select where 'id' > 3",
            "select where 'age' < 40",
            "select where 'age' != 5",
            "select where 'cost' >= 10",
            "Delete where 'id' = 4",
            "select where 'id' = 4",
            "deLEte",
            "seleCt"
    );
}
