package org.example;

public class Field {

    // Поле, состоящее из фишек.
    private Chip[][] field;
    // Первый игрок - человек.
    FirstPlayer firstPlayer;
    // Второй игрой - человек или компьютер.
    SecondPlayer secondPlayer;


    Field(int modeNumber) {
        // На поле играют два игрока своими фишками.
        firstPlayer = new FirstPlayer();
        if (modeNumber == 1) {
            secondPlayer = new SecondPlayer(false);
        } else {
            secondPlayer = new SecondPlayer(true);
        }

        // Создаём поле 8*8.
        field = new Chip[8][8];

        // Выставляем начальные фишки первого игрока.
        field[3][3] = new Chip(Chip.ChipType.Black);
        firstPlayer.FillingChips(new IntPair(3, 3));

        field[4][4] = new Chip(Chip.ChipType.Black);
        firstPlayer.FillingChips(new IntPair(4, 4));

        // Выставляем начальные фишки второго игрока
        // (не важно компьютер это или человек).
        field[3][4] = new Chip(Chip.ChipType.White);
        secondPlayer.FillingChips(new IntPair(3, 4));

        field[4][3] = new Chip(Chip.ChipType.White);
        secondPlayer.FillingChips(new IntPair(4, 3));
    }

    // Добавляем фишку в клетку на поле.
    public void setChipToField(IntPair s, Chip.ChipType type) {
        if (s.x < 8 && s.x >= 0 && s.y < 8 && s.y >= 0) {
            field[s.x][s.y] = new Chip(type);
        }
    }

    // Удаляем временную фишку с поля.
    public void deleteTemporaryChip(IntPair coordinates, Chip.ChipType type) {
        if (type == Chip.ChipType.Temporary) {
            field[coordinates.x][coordinates.y] = null;
        }
    }

    int getNumberOfBlackChips() {
        return firstPlayer.getCountOfChips();
    }

    int getNumberOfWhiteChips() {
        return secondPlayer.getCountOfChips();
    }

    // Печатаем игровое поле.
    public void printField() {
        System.out.println(String.format("\t\t\t\tWhite: %d\tBlack: %d",
                getNumberOfWhiteChips(), getNumberOfBlackChips()));
        for (int i = 0; i < field.length; i++) {
            System.out.println("  --------------------------------" +
                    "-------------------------------");
            System.out.print(i);
            for (int j = field[i].length - 1; j >=0; j--) {
                System.out.print(String.format(" |\t%s\t", (field[i][j] == null) ? ""
                        : ((field[i][j].isBlack()) ? "●" : ((field[i][j].isTemporary()) ? "◌" : "○"))));
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("  --------------------------------" +
                "-------------------------------");
        System.out.println("\t7\t\t6\t\t5\t\t4\t\t3\t\t2\t\t1\t\t0");
    }
}
