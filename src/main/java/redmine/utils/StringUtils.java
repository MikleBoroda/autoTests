package redmine.utils;

import java.util.Random;

public class StringUtils {

    public static final String EN_STRING = "abcdeifghijklmnop";
    private static final String LATIN_PATTERN = "abcdefghijklmnopqrstuvwxyz";
    public static final Random RANDOM = new Random();

    public static String randomHexString(int length) {
        return randomString(EN_STRING, length);
    }

    public static String randomEmail(){
        return randomEnglishString(6) + "@" + randomEnglishString(6) + "." + randomEnglishString(2);
    }

    public static String randomEnglishString(int length) {
        return randomString(LATIN_PATTERN, length);
    }

    public static String randomString(String pattern, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int patternLength = pattern.length();
            int random = RANDOM.nextInt(patternLength);
            char c = pattern.charAt(random);
            sb.append(c);
        }
        return sb.toString();
    }
}
