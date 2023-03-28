package com.digdes.school;

import java.util.regex.Pattern;

/**
 * Класс с постоянными значениями
 */
public class Constant {
    public static final String ID = "id";
    public static final String LAST_NAME = "lastname";
    public static final String AGE = "age";
    public static final String COST = "cost";
    public static final String ACTIVE = "active";
    public static final String WHERE = "where ";
    public static final String INSERT = "insert values";
    public static final String UPDATE = "update values";
    public static final String DELETE = "delete";
    public static final String SELECT = "select";
    public static final Pattern OPERATION = Pattern.compile("([\\w]+)([\\W]+)([\\w]+)");
    public static final String AND = "and";
    public static final String OR = "or";

    public static final String LIKE = "like";
    public static final String I_LIKE = "ilike";
}
