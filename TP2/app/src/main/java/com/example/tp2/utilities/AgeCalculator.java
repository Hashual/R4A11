package com.example.tp2.utilities;

public class AgeCalculator {

    public static int calculateAge(int year) {
        int currentYear = java.time.Year.now().getValue();
        return currentYear - year;
    }
}
