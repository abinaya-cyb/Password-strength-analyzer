package com.passwordstrengthanalyzer;

import java.util.ArrayList;
import java.util.List;

public final class PasswordStrengthAnalyzer {
    private static final int MAX_LENGTH_POINTS = 50;
    private static final int MAX_LENGTH_FOR_POINTS = 16;
    private static final int STRONG_SCORE = 85;
    private static final int MEDIUM_SCORE = 50;

    private PasswordStrengthAnalyzer() {
    }

    public static Analysis analyze(char[] password) {
        int length = password.length;
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (char character : password) {
            if (Character.isUpperCase(character)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(character)) {
                hasLowercase = true;
            } else if (Character.isDigit(character)) {
                hasNumber = true;
            } else {
                hasSpecialCharacter = true;
            }
        }

        int score = Math.min(length, MAX_LENGTH_FOR_POINTS) * MAX_LENGTH_POINTS
                / MAX_LENGTH_FOR_POINTS;
        if (hasUppercase) {
            score += 13;
        }
        if (hasLowercase) {
            score += 13;
        }
        if (hasNumber) {
            score += 12;
        }
        if (hasSpecialCharacter) {
            score += 12;
        }

        Strength strength = score >= STRONG_SCORE
                ? Strength.STRONG
                : score >= MEDIUM_SCORE ? Strength.MEDIUM : Strength.WEAK;

        List<String> suggestions = new ArrayList<>();
        if (length < 12) {
            suggestions.add("Use at least 12 characters; 16 or more is even better.");
        }
        if (!hasUppercase) {
            suggestions.add("Add an uppercase letter (A-Z).");
        }
        if (!hasLowercase) {
            suggestions.add("Add a lowercase letter (a-z).");
        }
        if (!hasNumber) {
            suggestions.add("Add a number (0-9).");
        }
        if (!hasSpecialCharacter) {
            suggestions.add("Add a special character, such as !, #, or %.");
        }
        if (suggestions.isEmpty()) {
            suggestions.add("Great mix of length and character types.");
            suggestions.add("Use a unique password that you do not reuse on other sites.");
        }

        return new Analysis(score, strength, List.copyOf(suggestions));
    }

    public enum Strength {
        WEAK("Weak"),
        MEDIUM("Medium"),
        STRONG("Strong");

        private final String label;

        Strength(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public record Analysis(int score, Strength strength, List<String> suggestions) {
    }
}
