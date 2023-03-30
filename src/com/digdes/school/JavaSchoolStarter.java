package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;


public class JavaSchoolStarter {

    public JavaSchoolStarter() {
    }

    /**
     * Коллекция с данными
     */
    private final ArrayList<Map<String, Object>> dataBase = new ArrayList<>();

    /**
     * Метод для обработки входных данных и выводе результата
     * @param request Команда на обработку
     * @return Результат выполнения метода
     * @throws Exception
     */
    public List<Map<String, Object>> execute(String request) throws Exception {
        if (request.toLowerCase().startsWith(Constant.INSERT)) {
            return addRow(request);
        } else if (request.toLowerCase().startsWith(Constant.UPDATE)) {
            return updateRow(request);
        } else if (request.toLowerCase().startsWith(Constant.DELETE)) {
            return deleteRow(request);
        } else if (request.toLowerCase().startsWith(Constant.SELECT)) {
            return selectRow(request);
        } else {
            throw new Exception("Некорректный воод команды");
        }
    }

    /**
     * Метод на добавление строки
     * @param request Команда с данными на добвавление в строку
     * @return Добавленая строка в List
     */
    private List<Map<String, Object>> addRow(String request) throws Exception {
        Map<String, Object> newRow = addInformationToMap(request, new HashMap<>());
        dataBase.add(newRow);
        return List.of(newRow);
    }

    /**
     * Метод для обновления данных в строках
     * @param request Команда с данными на обновление строк
     * @return List изменненых строк с новыми данными
     */
    private List<Map<String, Object>> updateRow(String request) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        if (request.toLowerCase().contains(Constant.WHERE)) {

            String[] splitRequest = request.split(Constant.WHERE);

            if (splitRequest[1].toLowerCase().contains(Constant.AND)) {

                String[] criterion = parsingOperation(splitRequest[1].split(Constant.AND)[0]);
                String[] criterion2 = parsingOperation(splitRequest[1].split(Constant.AND)[1]);

                for (int i = 0; i < dataBase.size(); i++) {
                    if (logicOperation(criterion, dataBase.get(i)) && logicOperation(criterion2, dataBase.get(i))) {
                        dataBase.set(i, addInformationToMap(splitRequest[0], dataBase.get(i)));
                        result.add(dataBase.get(i));
                    }
                }
            } else if (splitRequest[1].toLowerCase().contains(Constant.OR)) {

                String[] criterion = parsingOperation(splitRequest[1].split(Constant.OR)[0]);
                String[] criterion2 = parsingOperation(splitRequest[1].split(Constant.OR)[1]);

                for (int i = 0; i < dataBase.size(); i++) {
                    if (logicOperation(criterion, dataBase.get(i)) || logicOperation(criterion2, dataBase.get(i))) {
                        dataBase.set(i, addInformationToMap(splitRequest[0], dataBase.get(i)));
                        result.add(dataBase.get(i));
                    }
                }
            } else {
                String[] criterion = parsingOperation(splitRequest[1]);
                for (int i = 0; i < dataBase.size(); i++) {
                    if (logicOperation(criterion, dataBase.get(i))) {
                        dataBase.set(i, addInformationToMap(splitRequest[0], dataBase.get(i)));
                        result.add(dataBase.get(i));
                    }
                }
            }
        } else {
            for (int i = 0; i < dataBase.size(); i++) {
                dataBase.set(i, addInformationToMap(request, dataBase.get(i)));
                result.add(dataBase.get(i));
            }
        }

        return result;
    }

    /**
     * Метод для выбора строк
     * @param request Команда с данными на пролучение определенных строк
     * @return List строк с необходимыми данными
     */
    private List<Map<String, Object>> selectRow(String request) throws Exception {
        List<Map<String, Object>> responseList = new ArrayList<>();
        if (request.toLowerCase().contains(Constant.WHERE)) {

            String[] allCriterion = request.split(Constant.WHERE);

            if (allCriterion[1].toLowerCase().contains(Constant.AND)) {
                String[] criterion = parsingOperation(allCriterion[1].split(Constant.AND)[0]);
                String[] criterion2 = parsingOperation(allCriterion[1].split(Constant.AND)[1]);

                for (Map<String, Object> next : dataBase) {
                    if (logicOperation(criterion, next) && logicOperation(criterion2, next)) {
                        responseList.add(next);
                    }
                }
            } else if (allCriterion[1].toLowerCase().contains(Constant.OR)) {
                String[] criterion = parsingOperation(allCriterion[1].split(Constant.OR)[0]);
                String[] criterion2 = parsingOperation(allCriterion[1].split(Constant.OR)[1]);

                for (Map<String, Object> next : dataBase) {
                    if (logicOperation(criterion, next) || logicOperation(criterion2, next)) {
                        responseList.add(next);
                    }
                }
            } else {
                String[] criterion = parsingOperation(allCriterion[1]);

                for (Map<String, Object> next : dataBase) {
                    if (logicOperation(criterion, next)) {
                        responseList.add(next);
                    }
                }
            }
        } else {
            responseList.addAll(dataBase);
        }
        return responseList;
    }

    /**
     * Метод для удаление строк
     * @param request Команда с данными на удаление строк
     * @return List удалленых строк
     * @exception Exception Некорректный запрос с использованием WHERE
     */
    private List<Map<String, Object>> deleteRow(String request) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        if (request.toLowerCase().contains(Constant.WHERE)) {

            String[] allCriterion = request.split(Constant.WHERE);

            if (allCriterion[1].toLowerCase().contains(Constant.AND)) {
                String[] criterion = parsingOperation(allCriterion[1].split(Constant.AND)[0]);
                String[] criterion2 = parsingOperation(allCriterion[1].split(Constant.AND)[1]);
                Iterator<Map<String, Object>> dataBAseIterator = dataBase.iterator();

                while (dataBAseIterator.hasNext()) {
                    Map<String, Object> next = dataBAseIterator.next();
                    if (logicOperation(criterion, next) && logicOperation(criterion2, next)) {
                        result.add(next);
                        dataBAseIterator.remove();
                    }
                }
            } else if (allCriterion[1].toLowerCase().contains(Constant.OR)) {
                String[] criterion = parsingOperation(allCriterion[1].split(Constant.OR)[0]);
                String[] criterion2 = parsingOperation(allCriterion[1].split(Constant.OR)[1]);
                Iterator<Map<String, Object>> dataBAseIterator = dataBase.iterator();

                while (dataBAseIterator.hasNext()) {
                    Map<String, Object> next = dataBAseIterator.next();

                    if (logicOperation(criterion, next) || logicOperation(criterion2, next)) {
                        result.add(next);
                        dataBAseIterator.remove();
                    }
                }
            } else {
                String[] criterion = parsingOperation(allCriterion[1]);
                Iterator<Map<String, Object>> dataBAseIterator = dataBase.iterator();

                while (dataBAseIterator.hasNext()) {
                    Map<String, Object> next = dataBAseIterator.next();

                    if (logicOperation(criterion, next)) {
                        result.add(next);
                        dataBAseIterator.remove();
                    }
                }
            }
        } else if (!(request.toLowerCase().contains(Constant.WHERE))) {
            Iterator<Map<String, Object>> dataBaseIterator = dataBase.iterator();
            while (dataBaseIterator.hasNext()) {
                Map<String, Object> next = dataBaseIterator.next();
                result.add(next);
                dataBaseIterator.remove();
            }
        } else {
            throw new Exception("Некорректная команда на удаление с использование WHERE");
        }
        return result;
    }

    /**
     * Метод для удаления лишних символов из значений
     * @param word Столбец со значением
     * @return Значение
     */
    private String clearWord(String word) {
        return word.substring(word.indexOf("=") + 1).replaceAll("'| ", "");
    }

    /**
     * Метод для добавления или обновления данных во входящей Map
     * @param informationToAdd Столбец со значеним
     * @param row Строка для добавления/обновления данных
     * @return Строка с новыми данными
     * @exception Exception Некорректный запрос с наименованием столбца в строке
     */
    private Map<String, Object> addInformationToMap(String informationToAdd, Map<String, Object> row) throws Exception {
        for (String str : informationToAdd.split(",")) {
            if (str.toLowerCase().contains(Constant.ID)) {
                if (clearWord(str).equals("null")) {
                    row.remove(Constant.ID);
                } else {
                    row.put(Constant.ID, Long.valueOf(clearWord(str)));
                }
                continue;
            }
            if (str.toLowerCase().contains(Constant.LAST_NAME)) {
                if (clearWord(str).equals("null")) {
                    row.remove(Constant.LAST_NAME);
                } else {
                    row.put(Constant.LAST_NAME, clearWord(str));
                }
                continue;
            }
            if (str.toLowerCase().contains(Constant.AGE)) {
                if (clearWord(str).equals("null")) {
                    row.remove(Constant.AGE);
                } else {
                    row.put(Constant.AGE, Long.valueOf(clearWord(str)));
                }
                continue;
            }
            if (str.toLowerCase().contains(Constant.COST)) {
                if (clearWord(str).equals("null")) {
                    row.remove(Constant.COST);
                } else {
                    row.put(Constant.COST, Double.valueOf(clearWord(str)));
                }
                continue;
            }
            if (str.toLowerCase().contains(Constant.ACTIVE)) {
                if (clearWord(str).equals("null")) {
                    row.remove(Constant.ACTIVE);
                } else {
                    row.put(Constant.ACTIVE, Boolean.valueOf(clearWord(str)));
                }
            } else throw new Exception("Некорректный запрос");
        }
        return row;
    }

    /**
     * Метод для парсинга из String условий для логического сравнения
     * @param operation Условие логического сравнения в String
     * @return Массив с названием колонки, логическим оператором и значением
     * @exception Exception Некорректный операция сравнения
     */
    private String[] parsingOperation(String operation) throws Exception {
        String[] dataArray = new String[3];
        operation = operation.replaceAll("'| ", "");

        if (operation.toLowerCase().contains(Constant.I_LIKE)) {
            dataArray[0] = operation.split("ilike")[0].toLowerCase();
            dataArray[1] = Constant.I_LIKE;
            dataArray[2] = operation.split("ilike")[1];

        } else if (operation.contains(Constant.LIKE)) {
            dataArray[0] = operation.split("like")[0].toLowerCase();
            dataArray[1] = Constant.LIKE;
            dataArray[2] = operation.split("like")[1];

        } else {

            Matcher matcher = Constant.OPERATION.matcher(operation);
            if (matcher.find()) {
                for (int i = 0; i < 3; i++) {
                    dataArray[i] = matcher.group(i + 1);
                }
                dataArray[0] = dataArray[0].toLowerCase();
            } else {
                throw new Exception("Некорретная операция сравнения");
            }
        }
        return dataArray;
    }

    /**
     * Метод для обработки логических сравнений
     * @param criterion Массив с названием колонки, логическим оператором и значением
     * @param maps Строка для обработки логических сравнений
     * @return Соответвие строки логическому сравнению
     * @exception Exception Некорректный оператор сравнения
     */
    private boolean logicOperation(String[] criterion, Map<String, Object> maps) throws Exception {
        switch (criterion[1]) {
            case "=" -> {
                return isEquals(criterion, maps);
            }

            case "!=" -> {
                return isNotEquals(criterion, maps);
            }

            case "like" -> {
                return like(criterion, maps);
            }
            case "ilike" -> {
                return iLike(criterion, maps);
            }
            case ">=" -> {
                return greaterThanOrEqual(criterion, maps);
            }
            case "<=" -> {
                return lessThanOrEqual(criterion, maps);
            }
            case "<" -> {
                return less(criterion, maps);
            }
            case ">" -> {
                return more(criterion, maps);
            }
            default -> throw new Exception("Некорретный оператор сравнения");
        }
    }

    /**
     * Метод для обработки операции =
     * @param operation Массив с названием колонки, операцией сравнения и значением для сравнения
     * @param map Строка для обработки логического сравнения
     * @return Соответвие строки логическому сравнению
     * @exception Exception Некорректный запрос на операцию сравнения
     */
    private static boolean isEquals(String[] operation, Map<String, Object> map) throws Exception {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Long.valueOf(operation[2]).equals(Long.valueOf(map.get(operation[0]).toString()));
            }
            case Constant.LAST_NAME -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return operation[2].equals(map.get(operation[0]).toString());
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Double.valueOf(operation[2]).equals(Double.valueOf(map.get(operation[0]).toString()));
            }
            case Constant.ACTIVE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Boolean.valueOf(operation[2]).equals(Boolean.valueOf(map.get(operation[0]).toString()));
            }
            default -> throw new Exception("Некорректный запрос на операцию =");

        }
    }

    /**
     * Метод для обработки операции !=
     * @param operation Массив с названием колонки, операцией сравнения и значением для сравнения
     * @param map Строка для обработки логического сравнения
     * @return Соответвие строки логическому сравнению
     * @exception Exception Некорректный запрос на операцию сравнения
     */
    //добавить проверку на нуль!!!
    private static boolean isNotEquals(String[] operation, Map<String, Object> map) throws Exception {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return Long.valueOf(operation[2]).equals(Long.valueOf("0"));
                }
                return !(Long.valueOf(operation[2]).equals(Long.valueOf(map.get(operation[0]).toString())));
            }
            case Constant.LAST_NAME -> {
                if (map.get(operation[0]) == null) {
                    return operation[2].equals("0");
                }
                return !(operation[2].equals(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return Double.valueOf(operation[2]).equals(Double.valueOf("0"));
                }
                return !(Double.valueOf(operation[2]).equals(Double.valueOf(map.get(operation[0]).toString())));
            }
            case Constant.ACTIVE -> {
                if (map.get(operation[0]) == null) {
                    return Boolean.valueOf(operation[2]).equals(Boolean.valueOf("false"));
                }
                return !(Boolean.valueOf(operation[2]).equals(Boolean.valueOf(map.get(operation[0]).toString())));
            }
            default -> throw new Exception("Некорректный запрос на операцию !=");
        }
    }

    /**
     * Метод для обработки операции Like
     * @param operation Массив с названием колонки, операцией сравнения и значением для сравнения
     * @param map Строка для обработки логического сравнения
     * @return Соответвие строки логическому сравнению
     * @exception Exception Некорректный запрос на операцию сравнения
     */
    private static boolean like(String[] operation, Map<String, Object> map) throws Exception {
        if (operation[0].equalsIgnoreCase(Constant.LAST_NAME)) {

            if (map.get(operation[0]) == null) {
                return false;
            } else if (operation[2].matches("%([\\w]+)%")) {
                return map.get(operation[0]).toString().contains(operation[2].replaceAll("%", ""));
            } else if (operation[2].matches("%([\\w]+)")) {
                return map.get(operation[0]).toString().endsWith(operation[2].replaceAll("%", ""));
            } else if (operation[2].matches("([\\w]+)%")) {
                return map.get(operation[0]).toString().startsWith(operation[2].replaceAll("%", ""));
            } else {
                return operation[2].equals(map.get(operation[0]).toString());
            }

        } else {
            throw new Exception("Некорректный запрос на операцию like");
        }
    }

    /**
     * Метод для обработки операции ILike
     * @param operation Массив с названием колонки, операцией сравнения и значением для сравнения
     * @param map Строка для обработки логического сравнения
     * @return Соответвие строки логическому сравнению
     * @exception Exception Некорректный запрос на операцию сравнения
     */
    private static boolean iLike(String[] operation, Map<String, Object> map) throws Exception {
        if (operation[0].equalsIgnoreCase(Constant.LAST_NAME)) {
            operation[2] = operation[2].toLowerCase();
            if (map.get(operation[0]) == null) {
                return false;
            } else if (operation[2].matches("%([\\w]+)%")) {
                return map.get(operation[0]).toString().toLowerCase().contains(operation[2].replaceAll("%", ""));
            } else if (operation[2].matches("%([\\w]+)")) {
                return map.get(operation[0]).toString().toLowerCase().endsWith(operation[2].replaceAll("%", ""));
            } else if (operation[2].matches("([\\w]+)%")) {
                return map.get(operation[0]).toString().toLowerCase().startsWith(operation[2].replaceAll("%", ""));
            } else {
                return operation[2].equalsIgnoreCase(map.get(operation[0]).toString());
            }

        } else {
            throw new Exception("Некорректный запрос на операцию ilike");
        }
    }

    /**
     * Метод для обработки операции >=
     * @param operation Массив с названием колонки, операцией сравнения и значением для сравнения
     * @param map Строка для обработки логического сравнения
     * @return Соответвие строки логическому сравнению
     * @exception Exception Некорректный запрос на операцию сравнения
     */
    private static boolean greaterThanOrEqual(String[] operation, Map<String, Object> map) throws Exception {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Long.parseLong(operation[2]) <= (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Double.parseDouble(operation[2]) <= Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new Exception("Некорректный запрос на операцию >=");
        }
    }

    /**
     * Метод для обработки операции <=
     * @param operation Массив с названием колонки, операцией сравнения и значением для сравнения
     * @param map Строка для обработки логического сравнения
     * @return Соответвие строки логическому сравнению
     * @exception Exception Некорректный запрос на операцию сравнения
     */
    private static boolean lessThanOrEqual(String[] operation, Map<String, Object> map) throws Exception {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Long.parseLong(operation[2]) >= (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Double.parseDouble(operation[2]) >= Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new Exception("Некорректный запрос на операцию <=");
        }
    }

    /**
     * Метод для обработки операции <
     * @param operation Массив с названием колонки, операцией сравнения и значением для сравнения
     * @param map Строка для обработки логического сравнения
     * @return Соответвие строки логическому сравнению
     * @exception Exception Некорректный запрос на операцию сравнения
     */
    private static boolean less(String[] operation, Map<String, Object> map) throws Exception {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Long.parseLong(operation[2]) > (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Double.parseDouble(operation[2]) > Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new Exception("Некорректный запрос на операцию <");
        }
    }

    /**
     * Метод для обработки операции >
     * @param operation Массив с названием колонки, операцией сравнения и значением для сравнения
     * @param map Строка для обработки логического сравнения
     * @return Соответвие строки логическому сравнению
     * @exception Exception Некорректный запрос на операцию сравнения
     */
    private static boolean more(String[] operation, Map<String, Object> map) throws Exception {
        switch (operation[0]) {
            case Constant.ID, Constant.AGE -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Long.parseLong(operation[2]) < (Long.parseLong(map.get(operation[0]).toString()));
            }
            case Constant.COST -> {
                if (map.get(operation[0]) == null) {
                    return false;
                }
                return Double.parseDouble(operation[2]) < Double.parseDouble(map.get(operation[0]).toString());
            }
            default -> throw new Exception("Некорректный запрос на операцию >");
        }
    }
}
