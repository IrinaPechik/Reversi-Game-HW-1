package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

// Второй игрок - человек или компьютер.
public class SecondPlayer extends GamePlayer {
    private final String playerName;
    private final boolean isHuman;
    private List<IntPair> chips;
    public boolean hasMoves = true;

    // Создаём второго игрока - человек или компьютер.
    SecondPlayer(boolean isHuman) {
        chips = new ArrayList<IntPair>();
        this.isHuman = isHuman;
        if (isHuman) {
            playerName = "User#2";
        } else {
            playerName = "Computer";
        }
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public boolean isUserPlayer() {
        return isHuman;
    }

    @Override
    public void makeMove(Field field, Game game) {
        System.out.println("\nХод игрока: " + getPlayerName());
        if (isHuman) {
            // Показываем пользователю доступные ему ходы.
            game.getMoves(true, true);
            if (!hasMoves) {
                field.printField();
                System.out.println("У этого игрока больше нет ходов!");
                return;
            }
            // Второй человек-игрок делает ход.
            humanMove(field, game);
        } else {
            game.getMoves(true, false);
            if (!hasMoves) {
                System.out.println("У этого игрока больше нет ходов!");
                return;
            }
            IntPair chipForMove = findBestMove(game);
            ComputerMove(field, game, chipForMove);
            field.printField();
        }
    }

    // Компьютер совершает ход.
    public void ComputerMove(Field field, Game game, IntPair point) {
        try {
            IntPair enemyCoordinate = game.checkInputCoordinate(point);
            field.setChipToField(point, Chip.ChipType.White);
            field.setChipToField(enemyCoordinate, Chip.ChipType.White);
            // Захват вражеской фишки.
            field.firstPlayer.deleteElementFromListOfChips(enemyCoordinate);
            FillingChips(point);
            FillingChips(enemyCoordinate);
        } catch (NumberFormatException | IOException exception) {
            System.err.println("Error");
        }
    }
    // Поиск хода для компьютера.
    public IntPair findBestMove(Game game) {
        // Максимальное значение оценочной функции.
        int maxValue = -10;
        // Лучшая координата, в которой достигается максимум оценочной функции.
        IntPair bestCoordinate = new IntPair(0, 0);


        Map<IntPair, List<IntPair>> newMap = new HashMap<>();
        List<IntPair> j;

        // Переворачиваем словарь в newMap.
        for (Map.Entry<IntPair, List<IntPair>> entry : game.intersection.entrySet()) {
            j = new ArrayList<>();
            for (IntPair i : entry.getValue()) {
                IntPair startChip = entry.getKey();
                if (!newMap.containsKey(i)) {
                    j.add(startChip);
                    newMap.put(i, j);
                } else {
                    j = newMap.get(i);
                    j.add(startChip);
                    newMap.put(i, j);
                }
            }
        }

        for (Map.Entry<IntPair, List<IntPair>> entry : newMap.entrySet()) {
            IntPair key = entry.getKey();
            // Координаты, совпадающие по x, будут вместе на одном пути.
            List<IntPair> x = new ArrayList<>();
            // Координаты, совпадающие по y, будут вместе на одном пути.
            List<IntPair> y = new ArrayList<>();
            // Диагональные координаты, будут вместе на одном пути.
            List<IntPair> d = new ArrayList<>();

            int sign = 0;

            if ((key.x == 0 || (key.x == 7
                    || (key.y == 0 || (key.y == 7))))) {
                sign = 2;
            }

            for (IntPair i : entry.getValue()) {
                if (i.x == key.x) {
                    x.add(i);
                } else if (i.y == key.y) {
                    y.add(i);
                } else if ((Math.abs(key.x - i.x) == Math.abs(key.y - i.y))) {
                    d.add(i);
                }
            }

            // Ищем лучший результат.
            int rez = evaluationFunction(x, sign);

            if (rez > maxValue) {
                maxValue = rez;
                bestCoordinate = key;
            }
            rez = evaluationFunction(y, sign);
            if (rez > maxValue) {
                maxValue = rez;
                bestCoordinate = key;
            }

            rez = evaluationFunction(d, 1);
            if (rez > maxValue) {
                maxValue = rez;
                bestCoordinate = key;
            }
        }
        return bestCoordinate;
    }

    // Оцениваем варианты ходов.
    public int evaluationFunction(List<IntPair> sp, int sign) {
        int sum = 0;
        int n = sp.size();
        double ss = 0;
        int s1 = 1;

        // Делаем диагональный ход.
        if (sign == 1) {
            ss = 0.8;
        } else if (sign == 2) {
            ss = 0.4;
        }

        for (int i = 1; i <= n; i++) {
            if ((sp.get(i - 1).x == 0 || (sp.get(i - 1).x == 7
                    || (sp.get(i - 1).y == 0 || (sp.get(i - 1).y == 7))))) {
                s1 = 2;
            }
            sum += s1 + ss;
        }
        return sum;
    }

    @Override
    public void FillingChips(IntPair x) {
        chips.add(x);
    }

    @Override
    public List<IntPair> getListOfChips() {
        return chips;
    }

    @Override
    public void deleteElementFromListOfChips(IntPair coordinate) {
        chips.remove(coordinate);
    }

    @Override
    public int getCountOfChips() {
        return chips.size();
    }

    // Ход делает второй игрок - человек.
    private void humanMove(Field field, Game game) {
        int x, y;
        System.out.println("Сделайте ход");
        // Читаем координаты, введённые пользователем.
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("Введите x:");
                x = Integer.parseInt(bufferedReader.readLine());
                System.out.println("Введите y:");
                y = Integer.parseInt(bufferedReader.readLine());
                IntPair newMove = new IntPair(x, y);

                IntPair enemyCoordinate = game.checkInputCoordinate(newMove);
                field.setChipToField(newMove, Chip.ChipType.White);
                System.out.println(enemyCoordinate);
                field.setChipToField(enemyCoordinate, Chip.ChipType.White);
                // Захват вражеской фишки.
                field.firstPlayer.deleteElementFromListOfChips(enemyCoordinate);
                FillingChips(newMove);
                FillingChips(enemyCoordinate);
                game.intersection = null;
                break;
            } catch (NumberFormatException | IOException exception) {
                System.err.println("Invalid Data!");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.err.println("Error!");
                }
            }
        }
    }

    @Override
    public boolean hasAnyMoves() {
        return hasMoves;
    }
    @Override
    public boolean isInListOfChips(IntPair intPair) {
        return chips.contains(intPair);
    }
}
