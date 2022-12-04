package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {
    Menu() {
        int choice = 1;
        do {
            PrintMenu();
            MakeChoice();
            System.out.println("Хотите продожить? Если да - введите 1\nЕсли нет - введите что угодно.");
            // Читаем выбор пользователем.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                try {
                    choice = Integer.parseInt(bufferedReader.readLine());
                    break;
                } catch (NumberFormatException | IOException exception) {
                    System.err.println("Invalid Format!");
                }
            }

        } while (choice == 1);

    }
    public void PrintMenu() {
        System.out.println("Выберите режим игры:\n#1 - Легкий\n" +
                "#2 - Игрок против игорока");
    }

    Game game;
    public void MakeChoice() {
        // Номер режима работы.
        int modeNumber = 0;

        // Читаем выбор режима пользователем.
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                modeNumber = Integer.parseInt(bufferedReader.readLine());
                break;
            } catch (NumberFormatException | IOException exception) {
                System.err.println("Invalid Format!");
            }
        }

        // Вызываем нужный режим.
        switch (modeNumber) {
            case 1 -> game = new Game(1);
            case 2 -> game = new Game(2);
            default -> System.err.println("Invalid Input!");
        }
    }
}
