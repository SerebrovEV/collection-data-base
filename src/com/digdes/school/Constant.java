package com.digdes.school;

import java.util.regex.Pattern;

public class Constant {
    public static final String ID = "id";
    public static final String LAST_NAME = "lastname";
    public static  final String AGE = "age";
    public static  final String COST = "cost";
    public static  final String ACTIVE = "active";
    public static  final String WHERE = "where";
    public static  final String INSERT = "insert values";
    public static  final String UPDATE = "update values";
    public static  final String DELETE = "delete";
    public static  final String SELECT = "select";
    public static  final Pattern OPERATION = Pattern.compile("'([\\w]+)'(\\W)([\\S]+)");
    public static  final Pattern AND = Pattern.compile("and");
    public static  final Pattern OR = Pattern.compile("or");
}
