package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// Игрок - человек.
public class FirstPlayer extends GamePlayer {
    private List<IntPair> chips;
    public boolean hasMoves = true;
    FirstPlayer() {
        chips = new ArrayList<>();
    }

    @Override
    public String getPlayerName() {
        return "User#1";
    }

    @Override
    public boolean isUserPlayer() {
        return true;
    }

    @Override
    public void makeMove(Field field, Game game) {
        System.out.println("Ход игрока: " + getPlayerName());
        // Показываем пользователю доступные ему ходы.
        game.getMoves(false, true);
        if (!hasMoves) {
            field.printField();
            System.out.println("У этого игрока больше нет ходов!");
            return;
        }
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
                field.setChipToField(newMove, Chip.ChipType.Black);

                field.setChipToField(enemyCoordinate, Chip.ChipType.Black);

                field.secondPlayer.deleteElementFromListOfChips(enemyCoordinate);
                FillingChips(newMove);
                FillingChips(enemyCoordinate);
                game.intersection = null;
                break;
            } catch (NumberFormatException | IOException exception) {
                System.err.println("Invalid Data!");
            }
        }
    }

    @Override
    public void FillingChips(IntPair x) {
        this.chips.add(x);
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

    @Override
    public boolean hasAnyMoves() {
        return hasMoves;
    }

    @Override
    public boolean isInListOfChips(IntPair intPair) {
        return chips.contains(intPair);
    }
}
