package org.inventory.util;

import java.util.Objects;
import java.util.Scanner;

public class ReadFromConsole {
    private static ReadFromConsole readFromConsole;
    private static Scanner scanner;

    private static String readMatchingStringFromConsole(String regex, String error) {
        String txt = "";
        boolean isMatching;
        do {
            txt = scanner.nextLine();
            isMatching = txt.matches(regex);
            if (!isMatching) {
                System.out.printf(error, txt);
            }
        } while (!isMatching);
        return txt;
    }

    public static ReadFromConsole getInstance() {
        if (Objects.isNull(readFromConsole)) {
            readFromConsole = new ReadFromConsole();
            scanner = new Scanner(System.in);
        }
        return readFromConsole;
    }


    public int readInteger() {
        return Integer.parseInt(readMatchingStringFromConsole("((\\d)+)", "%s is not valid digit number\n"));
    }

    public double readDouble() {
        return Double.parseDouble(readMatchingStringFromConsole("((\\d)+)(\\.((\\d)+))?", "%s is not valid decimal number\n"));
    }

    public String readString() {
        return scanner.nextLine();
    }

    public String readPass() {
        String matcher = "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,}$";
        String error = "Your \"%d\" password should have:\n" +
                "                    at least one upper case English letter\n" +
                "                    at least one lower case English letter\n" +
                "                    at least one digit\n" +
                "                    at least one special character\n" +
                "                    minimum eight characters";
        return readMatchingStringFromConsole(matcher, error);
    }

    public String readDate() {
        String matcher = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
        String error = "Please provide valid date of format: yyyy-mm-dd";
        return readMatchingStringFromConsole(matcher, error);
    }
}

