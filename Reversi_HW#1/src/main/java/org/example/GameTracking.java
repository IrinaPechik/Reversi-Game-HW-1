package org.example;

public class GameTracking {
    private static int firstBest = 0;

    public static int getBestFirst() {
        return firstBest;
    }
    private static int secondBest = 0;

    public static int getBestSecond() {
        return secondBest;
    }
    public static boolean isGameFinished(Field field) {
        return !(field.firstPlayer.hasMoves || field.secondPlayer.hasMoves);
    }

    public static String getWinner(Field field) {
        int firstCount = field.firstPlayer.getCountOfChips();
        int secondCount = field.secondPlayer.getCountOfChips();

        if (firstCount > firstBest) {
            firstBest = firstCount;
        }

        if (secondCount > secondBest) {
            secondBest = secondCount;
        }
        String firstName = field.firstPlayer.getPlayerName();
        String secondName = field.secondPlayer.getPlayerName();

        if (firstCount > secondCount) {
            return String.format("Победитель %s\nСчёт:\n%s: %d (Лучший его результат = %d)\n%s: %d(Лучший его результат = %d)\n", firstName,
                    firstName, firstCount, getBestFirst(), secondName, secondCount, getBestSecond());
        } else if (secondCount > firstCount) {
            return String.format("Победитель %s\nСчёт:\n%s: %d (Лучший его результат = %d)\n%s: %d(Лучший его результат = %d)\n", secondName,
                    firstName, firstCount, getBestFirst(), secondName, secondCount, getBestSecond());
        } else {
            return String.format("Ничья\nСчёт:\n%s: %d (Лучший его результат = %d)\n%s: %d(Лучший его результат = %d)\n",
                    firstName, firstCount, getBestFirst(), secondName, secondCount, getBestSecond());
        }
    }


}
