package org.example;

// Класс, описывающий КОНКРЕТНУЮ фишку на игровом поле.
public class Chip {
    ChipType chipType = ChipType.Temporary;
    Chip(ChipType chipType) {
        this.chipType = chipType;
    }

    // Является ли фишка временной.
    boolean isTemporary() {
        return chipType.getValue() == -1;
    }

    // Является ли фишка чёрной.
    boolean isBlack() {
        return chipType.getValue() == 0;
    }

    // Тип фишки: черная, белая или временная.
    enum ChipType {
        Black(0), White(1), Temporary(-1);
        private final int chipType;
        ChipType(int chipType) {
            this.chipType = chipType;
        }
        int getValue() {
            return chipType;
        }
    }
}
