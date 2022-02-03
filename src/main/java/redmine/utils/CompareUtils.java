package redmine.utils;

import io.qameta.allure.Step;
import redmine.allure.asserts.AllureAssert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static redmine.allure.asserts.AllureAssert.*;

public class CompareUtils {
    private static final Comparator<String> DATE_DESC_COMPARATOR = (s1, s2) -> {
        LocalDateTime d1 = LocalDateTime.parse(s1, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        LocalDateTime d2 = LocalDateTime.parse(s2, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        return d2.compareTo(d1);
    };

    private static final Comparator<String> USER_DESC_COMPARATOR = (s1, s2) -> s2.compareToIgnoreCase(s1);
    private static final Comparator<String> USER_ASC_COMPARATOR = USER_DESC_COMPARATOR.reversed();

    @Step("Проверка сортировки списка дат по убыванию")
    public static void assertListSortedByDateDesc(List<String> dates) {
        List<String> sortedDates = new ArrayList<>(dates);
        sortedDates.sort(DATE_DESC_COMPARATOR);
        assertEquals(dates, sortedDates);
    }

    public static Comparator<String> getComparator(String comparatorType) {
        switch (comparatorType) {
            case ("По возрастанию"):
                return USER_ASC_COMPARATOR;
            case ("По убыванию"):
                return USER_DESC_COMPARATOR;
        }
        throw new IllegalArgumentException("Такой сортировки не существует");
    }
}
