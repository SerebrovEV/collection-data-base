package com.digdes.school;


import java.util.List;

public class Main {

    public static void main(String[] args) {

        JavaSchoolStarter starter = new JavaSchoolStarter();
        List<String> commands = List.of(
                "INSERT VALUES 'LastName' = ' Федоров' , 'id'= 3, 'age'= 402, 'active'= true, 'cost'=4.5",
                "INSERT VALUES 'LastName' = ' Федоров' , 'id'= 3, 'age'= 402, 'active'= true, 'cost'=4.7",
                "INSERT VALUES 'LastName' = ' Федоров' , 'id'= 3, 'age'= 402, 'active'= true, 'cost'=4.8",
                "INSERT VALUES 'LastName' = ' Федоров2' , 'id'= 37, 'age'= 41, 'active'= false",
                "INSERT VALUES 'LastName' = ' Федоровa' , 'id'= 33, 'age'= 10, 'active'= true",
                "INSERT VALUES 'LastName' = ' Федоровsaf' , 'id'= 345, 'age'= 480, 'active'= false",
                "INSERT VALUES 'LastName' = ' Федороfsdв' , 'id'= 34, 'age'= 40, 'active'= true",
                "INSERT VALUES 'LastName' = ' Федоровdfs' , 'id'= 37, 'age'= 430, 'active'= true",
                //  "UPDATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3",
                //   "UPdATE VALUES 'active'=false, 'cost'=10.1 where 'id'=3",
                "Delete where 'id' = 3"
                // "Select where 'id'=3"
        );
        try {

            for (String str : commands) {
                System.out.println(starter.execute(str));
                //    System.out.println(starter.execute("INSERT VALUES 'LastName' = 'Федоров' , 'id'=    3, 'age'= 40, 'active'= true"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }


    }
}
