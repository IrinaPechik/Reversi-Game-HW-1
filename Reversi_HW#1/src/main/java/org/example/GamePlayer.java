package org.example;
import java.util.List;
// Абстрактный класс, описывающий игрока.
public abstract class GamePlayer {

    // У игрока есть лист своих расставленных фишек.
    public List<IntPair> listOfChips;

    // Имя игрока.
    public abstract String getPlayerName();

    // Является ли игрок человеком.
    public abstract boolean isUserPlayer();

    // Игрок делает ход.
    public abstract void makeMove(Field field, Game game);

    // Добавление своей фишки на поле.
    public abstract void FillingChips(IntPair x);

    // Получить все фишки игрока.
    public abstract List<IntPair> getListOfChips();
    // Удаляет фишку у данного игорка.
    public abstract void deleteElementFromListOfChips(IntPair coordinate);
    // Количество фишек данного игрока на поле.
    public abstract int getCountOfChips();
    // Проверка на наличие доступных ходов.
    public abstract boolean hasAnyMoves();
    public abstract boolean isInListOfChips(IntPair intPair);
}
