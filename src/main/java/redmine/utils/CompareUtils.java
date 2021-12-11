package redmine.utils;

import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CompareUtils {

    private static final Comparator<String> DATE_DESC_COMPARATOR = (s1, s2) -> {
        LocalDateTime d1 = LocalDateTime.parse(s1, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        LocalDateTime d2 = LocalDateTime.parse(s2, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        return d2.compareTo(d1);
    };

    private static final Comparator<String> DATE_ASC_COMPARATOR = DATE_DESC_COMPARATOR.reversed();

    private static final Comparator<String> USER_DESC_COMPARATOR = (s1, s2) -> s2.compareToIgnoreCase(s1);
    private static final Comparator<String> USER_ASC_COMPARATOR = USER_DESC_COMPARATOR.reversed();

    // сортировка дата создания по возрастанию
    public static void assertListSortedByDateDesc(List<String> dates) {
        List<String> sortedDates = new ArrayList<>(dates);
        sortedDates.sort(DATE_DESC_COMPARATOR);
        Assert.assertEquals(dates, sortedDates);

    }

    // сортировка дата создания по убыванию
    public static void assertListSortedByDateAsc(List<String> dates) {
        List<String> sortedDates = new ArrayList<>(dates);
        sortedDates.sort(DATE_ASC_COMPARATOR);
        Assert.assertEquals(dates, sortedDates);
    }

    // сортировка логина по убыванию
    public static void assertListSortedByUserNameDesc(List<String> userName) {
        List<String> sortedUserNAme = new ArrayList<>(userName);
        sortedUserNAme.sort(USER_DESC_COMPARATOR);
        Assert.assertEquals(userName, sortedUserNAme);
    }

    // сортировка логина по возрастанию
    public static void assertListSortedByUserNameAsc(List<String> userName) {
        List<String> sortedUserNAme = new ArrayList<>(userName);
        sortedUserNAme.sort(USER_ASC_COMPARATOR);
        Assert.assertEquals(userName, sortedUserNAme);
    }

    // сортировка по имени пользователя по убыванию
    public static void assertListSortedByFirstNameDesc(List<String> firstName) {
        List<String> sortedFirstName = new ArrayList<>(firstName);
        sortedFirstName.sort(USER_DESC_COMPARATOR);
        Assert.assertEquals(firstName, sortedFirstName);
    }

    // сортировка по имени пользователя по возрастанию
    public static void assertListSortedByFirstNameAsc(List<String> firstName) {
        List<String> sortedFirstName = new ArrayList<>(firstName);
        sortedFirstName.sort(USER_ASC_COMPARATOR);
        Assert.assertEquals(firstName, sortedFirstName);
    }

    // сортировка по фамили пользователя по убыванию
    public static void assertListSortedByLastNameDesc(List<String> lastName) {
        List<String> sortedLastName = new ArrayList<>(lastName);
        sortedLastName.sort(USER_DESC_COMPARATOR);
        Assert.assertEquals(lastName, sortedLastName);
    }

    // сортировка по фамили пользователя по возрастанию
    public static void assertListSortedByLastNameAsc(List<String> lastName) {
        List<String> sortedLastName = new ArrayList<>(lastName);
        sortedLastName.sort(USER_ASC_COMPARATOR);
        Assert.assertEquals(lastName, sortedLastName);
    }

    public static Boolean checkSortedList(List<String> list) {
        List<String> listSorted = new ArrayList<>(list);
        Collections.sort(listSorted);
        return listSorted.equals(list);
    }


}
