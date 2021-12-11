package redmine.utils;

import org.testng.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CompareUtils {

    private static final Comparator<String> DATE_DESC_COMPARATOR = (s1, s2) -> {
        LocalDateTime d1 = LocalDateTime.parse(s1, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        LocalDateTime d2 = LocalDateTime.parse(s2, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        return d2.compareTo(d1);
    };

    private static final Comparator<String> DATE_ASC_COMPARATOR = DATE_DESC_COMPARATOR.reversed();

    private static final Comparator<String> USER_NAME_DESC_COMPARATOR = (s1, s2) -> s2.compareToIgnoreCase(s1);
    private static final Comparator<String> USER_NAME_ASC_COMPARATOR = USER_NAME_DESC_COMPARATOR.reversed();

    public static void assertListSortedByDateDesc(List<String> dates) {
        List<String> sortedDates = new ArrayList<>(dates);
        sortedDates.sort(DATE_DESC_COMPARATOR);
        Assert.assertEquals(dates, sortedDates);

    }

    public static void assertListSortedByDateAsc(List<String> dates) {
        List<String> sortedDates = new ArrayList<>(dates);
        sortedDates.sort(DATE_ASC_COMPARATOR);
        Assert.assertEquals(dates, sortedDates);
    }

    public static void assertListSortedByUserNameDesc(List<String> userName) {
        List<String> sortedUserNAme = new ArrayList<>(userName);
        sortedUserNAme.sort(USER_NAME_DESC_COMPARATOR);
        Assert.assertEquals(userName, sortedUserNAme);
    }

    public static void assertListSortedByUserNameAsc(List<String> userName) {
        List<String> sortedUserNAme = new ArrayList<>(userName);
        sortedUserNAme.sort(USER_NAME_ASC_COMPARATOR);
        Assert.assertEquals(userName, sortedUserNAme);
    }


}
