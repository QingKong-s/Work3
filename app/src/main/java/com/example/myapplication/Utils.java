package com.example.myapplication;

public class Utils {
    public static Integer convertPrefixToInt(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        StringBuilder numberPart = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isDigit(ch)) {
                numberPart.append(ch);
            } else {
                break;
            }
        }

        if (numberPart.length() == 0) {
            return 0;
        }

        try {
            return Integer.parseInt(numberPart.toString());
        } catch (NumberFormatException e) {
            System.out.println("数字超出范围");
            return 0;
        }
    }
}
