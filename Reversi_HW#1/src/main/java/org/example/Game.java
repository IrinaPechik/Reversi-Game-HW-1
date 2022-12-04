package org.example;
import java.io.IOException;
import java.util.*;

public class Game {

    // Игровое поле.
    private Field field;

    // Все ходы для первого игрока.
    List<IntPair> movesForFirst;
    // Все ходы для второго игрока.
    Map<IntPair, List<IntPair>> movesForSecond;
    // Все доступные ходы пользователю (пересечение).
    Map<IntPair, List<IntPair>> intersection;
    // Режим игры, выбранный пользователем вначале.
    private int modeNumber;

    public Game(int modeNumber) {
        this.modeNumber = modeNumber;
        // Создаём игровое поле.
        field = new Field(modeNumber);
        while (!GameTracking.isGameFinished(field)) {
            // Последовательно пользователи делают ход.
            makeMove();
        }
        System.out.println("Конец игры.\n" + GameTracking.getWinner(field));
    }

    private boolean isCorrectPoint(IntPair point) {
        if (point.x < 8 && point.x >= 0 && point.y < 8 && point.y >= 0) {
            return true;
        }
        return false;
    }
    // Находит возможные ходы из данной клетки с заданным расстоянием (1 или 2).
    public List<IntPair> FindAllMoves(IntPair point, int distance, boolean shouldSwap) {
        IntPair temporaryPair;
        // Создаём множество клеток.
        List<IntPair> allAvailableChips = new ArrayList<>();

        // Вправо-влево по x.
        temporaryPair = new IntPair(point.x + distance, point.y);
        if (isCorrectPoint(temporaryPair)) {
            if (canAddToMoves(temporaryPair)) {
                if (distance == 2) {
                    if (shouldSwap) {
                        if (!field.secondPlayer.isInListOfChips(new IntPair(point.x + 1, point.y))) {
                            if (field.firstPlayer.isInListOfChips(new IntPair(point.x + 1, point.y))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    } else {
                        if (!field.firstPlayer.isInListOfChips(new IntPair(point.x + 1, point.y))) {
                            if (field.secondPlayer.isInListOfChips(new IntPair(point.x + 1, point.y))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    }
                } else {
                    allAvailableChips.add(temporaryPair);
                }
            }
        }

        temporaryPair = new IntPair(point.x - distance, point.y);
        if (isCorrectPoint(temporaryPair)) {
            if (canAddToMoves(temporaryPair)) {
                if (distance == 2) {
                    if (shouldSwap) {
                        if (!field.secondPlayer.isInListOfChips(new IntPair(point.x - 1, point.y))) {
                            if (field.firstPlayer.isInListOfChips(new IntPair(point.x - 1, point.y))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    } else {
                        if (!field.firstPlayer.isInListOfChips(new IntPair(point.x - 1, point.y))) {
                            if (field.secondPlayer.isInListOfChips(new IntPair(point.x - 1, point.y))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    }
                } else {
                    allAvailableChips.add(temporaryPair);
                }
            }
        }

        // Вверх-вниз по y.
        temporaryPair = new IntPair(point.x, point.y + distance);
        if (isCorrectPoint(temporaryPair)) {
            if (canAddToMoves(temporaryPair)) {
                if (distance == 2) {
                    if (shouldSwap) {
                        if (!field.secondPlayer.isInListOfChips(new IntPair(point.x, point.y + 1))) {
                            if (field.firstPlayer.isInListOfChips(new IntPair(point.x, point.y + 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    } else {
                        if (!field.firstPlayer.isInListOfChips(new IntPair(point.x, point.y + 1))) {
                            if (field.secondPlayer.isInListOfChips(new IntPair(point.x, point.y + 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    }
                } else {
                    allAvailableChips.add(temporaryPair);
                }
            }
        }

        temporaryPair = new IntPair(point.x, point.y - distance);
        if (isCorrectPoint(temporaryPair)) {
            if (canAddToMoves(temporaryPair)) {
                if (distance == 2) {
                    if (shouldSwap) {
                        if (!field.secondPlayer.isInListOfChips(new IntPair(point.x, point.y - 1))) {
                            if (field.firstPlayer.isInListOfChips(new IntPair(point.x, point.y - 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    } else {
                        if (!field.firstPlayer.isInListOfChips(new IntPair(point.x, point.y - 1))) {
                            if (field.secondPlayer.isInListOfChips(new IntPair(point.x, point.y - 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    }
                } else {
                    allAvailableChips.add(temporaryPair);
                }
            }
        }

        // Вправо-вниз по диагонали.
        temporaryPair = new IntPair(point.x + distance, point.y - distance);
        if (isCorrectPoint(temporaryPair)) {
            if (canAddToMoves(temporaryPair)) {
                if (distance == 2) {
                    if (shouldSwap) {
                        if (!field.secondPlayer.isInListOfChips(new IntPair(point.x + 1, point.y - 1))) {
                            if (field.firstPlayer.isInListOfChips(new IntPair(point.x + 1, point.y - 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    } else {
                        if (!field.firstPlayer.isInListOfChips(new IntPair(point.x + 1, point.y - 1))) {
                            if (field.secondPlayer.isInListOfChips(new IntPair(point.x + 1, point.y - 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    }
                } else {
                    allAvailableChips.add(temporaryPair);
                }
            }
        }

        // Влево-вверх по диагонали.
        temporaryPair = new IntPair(point.x - distance, point.y + distance);
        if (isCorrectPoint(temporaryPair)) {
            if (canAddToMoves(temporaryPair)) {
                if (distance == 2) {
                    if (shouldSwap) {
                        if (!field.secondPlayer.isInListOfChips(new IntPair(point.x - 1, point.y + 1))) {
                            if (field.firstPlayer.isInListOfChips(new IntPair(point.x - 1, point.y + 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    } else {
                        if (!field.firstPlayer.isInListOfChips(new IntPair(point.x - 1, point.y + 1))) {
                            if (field.secondPlayer.isInListOfChips(new IntPair(point.x - 1, point.y + 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    }
                } else {
                    allAvailableChips.add(temporaryPair);
                }
            }
        }

        // Влево-вниз по диагонали.
        temporaryPair = new IntPair(point.x - distance, point.y - distance);
        if (isCorrectPoint(temporaryPair)) {
            if (canAddToMoves(temporaryPair)) {
                if (distance == 2) {
                    if (shouldSwap) {
                        if (!field.secondPlayer.isInListOfChips(new IntPair(point.x - 1, point.y - 1))) {
                            if (field.firstPlayer.isInListOfChips(new IntPair(point.x - 1, point.y - 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    } else {
                        if (!field.firstPlayer.isInListOfChips(new IntPair(point.x - 1, point.y - 1))) {
                            if (field.secondPlayer.isInListOfChips(new IntPair(point.x - 1, point.y - 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    }
                } else {
                    allAvailableChips.add(temporaryPair);
                }
            }
        }

        // Вправо-вверх по диагонали.
        temporaryPair = new IntPair(point.x + distance, point.y + distance);
        if (isCorrectPoint(temporaryPair)) {
            if (canAddToMoves(temporaryPair)) {
                if (distance == 2) {
                    if (shouldSwap) {
                        if (!field.secondPlayer.isInListOfChips(new IntPair(point.x + 1, point.y + 1))) {
                            if (field.firstPlayer.isInListOfChips(new IntPair(point.x + 1, point.y + 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    } else {
                        if (!field.firstPlayer.isInListOfChips(new IntPair(point.x + 1, point.y + 1))) {
                            if (field.secondPlayer.isInListOfChips(new IntPair(point.x + 1, point.y + 1))) {
                                allAvailableChips.add(temporaryPair);
                            }
                        }
                    }
                } else {
                    allAvailableChips.add(temporaryPair);
                }
            }
        }

        return allAvailableChips;
    }

    // Возвращает текущие доступные ходы пользователю.
    public void getMoves(boolean shouldSwap, boolean isHuman) {
        intersection = new HashMap<>();
        movesForFirst = new ArrayList<>();
        movesForSecond = new HashMap<>();
        if (shouldSwap) {
            // Находим все доступные ходы для первого игрока (не учитывая второго, а лишь расстояние).
            for (IntPair i : field.secondPlayer.getListOfChips()) {
                movesForFirst.addAll(FindAllMoves(i, 2, true));
            }

            // Все доступные ходы для второго игрока (не учитывая второго, а лишь расстояние)
            // + запоминаем из какой фишки сделали этот ход.
            for (IntPair i : field.firstPlayer.getListOfChips()) {
                movesForSecond.put(i, FindAllMoves(i, 1, true));
            }
        } else {
            // Находим все доступные ходы для первого игрока (не учитывая второго, а лишь расстояние).
            for (IntPair i : field.firstPlayer.getListOfChips()) {
                movesForFirst.addAll(FindAllMoves(i, 2, false));
            }

            // Все доступные ходы для второго игрока (не учитывая второго, а лишь расстояние)
            // + запоминаем из какой фишки сделали этот ход.
            for (IntPair i : field.secondPlayer.getListOfChips()) {
                movesForSecond.put(i, FindAllMoves(i, 1, false));
            }
        }

        // Находим пересечение (это как раз и будут все возможные ходы)
        for (Map.Entry<IntPair, List<IntPair>> entry : movesForSecond.entrySet()) {
            // Временный лист для каждого ключа, в котором мы будем хранить пересечение
            // ходов двух фишек (своей и врага).
            List<IntPair> entryValue = new ArrayList<IntPair>();
            for (IntPair pair : entry.getValue()) {
                if (movesForFirst.contains(pair)) {
                    entryValue.add(pair);
                }
            }
            // Кладём в словарь ключ - белая вершина, которая может быть перекрашена, если
            // пользователь выберет соответствующий ей ход, и сам доступный ход.
            // Делаю это для удобного перекрашивания впоследствии.
            intersection.put(entry.getKey(), entryValue);
        }


        // Заполняем возможные ходы в наше поле (их нужно почистить потом)
        List<IntPair> allMoves = new ArrayList<>();
        for (Map.Entry<IntPair, List<IntPair>> entry : intersection.entrySet()) {
            for (IntPair i : entry.getValue()) {
                if(!allMoves.contains(i)) {
                    allMoves.add(i);
                }
                field.setChipToField(i, Chip.ChipType.Temporary);
            }
        }
        if (allMoves.isEmpty()) {
            if (shouldSwap) {
                field.secondPlayer.hasMoves = false;
            } else {
                field.firstPlayer.hasMoves = false;
            }
            return;
        }
        if (isHuman) {
            System.out.println("Доступные ходы:");
            if (allMoves.isEmpty()) {
                System.out.println("Нет ходов");
                return;
            } else {
                for (IntPair i : allMoves) {
                    System.out.println(i);
                }
            }
        }
        // Печатаем поле с возможными ходами.
        field.printField();

        // Удаляем из поля все временные ходы.
        for (Map.Entry<IntPair, List<IntPair>> entry : intersection.entrySet()) {
            for (IntPair i : entry.getValue()) {
                field.deleteTemporaryChip(i, Chip.ChipType.Temporary);
            }
        }
        movesForSecond = null;
        movesForFirst = null;
    }

    // Каждый делает ход.
    public void makeMove() {
        // Игроки по очереди совершают ход.
        // При этом совершается проверка на корректность  введённых данных.
        field.firstPlayer.makeMove(field, this);
        field.secondPlayer.makeMove(field, this);
    }

    // Проверить результат ввода данных пользователя и вернуть координату фишки,
    // которую захватил игок и которую нужно перекрасить.
    public IntPair checkInputCoordinate(IntPair coordinate) throws IOException {
        boolean flag = false;
        IntPair pair = new IntPair(0, 0);

        for (Map.Entry<IntPair, List<IntPair>> entry : intersection.entrySet()) {
            if (entry.getValue().contains(coordinate)) {
                flag = true;
                pair = entry.getKey();
                int raznX, raznY;
                raznX = coordinate.x - pair.x;
                raznY = coordinate.y - pair.y;
                if (field.firstPlayer.isInListOfChips(new IntPair(pair.x - raznX, pair.y - raznY))
                        || field.secondPlayer.isInListOfChips(new IntPair(pair.x - raznX, pair.y - raznY))) {
                    break;
                }
            }
        }
        if (!flag) {
            throw new IOException();
        }
        return pair;
    }

    private boolean canAddToMoves(IntPair intPair) {
        if (field.firstPlayer.isInListOfChips(intPair)) {
            return false;
        } else if (field.secondPlayer.isInListOfChips(intPair)) {
            return false;
        }
        return true;
    }

}
