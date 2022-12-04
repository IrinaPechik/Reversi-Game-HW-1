package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаём меню.
            new Menu();
        } catch(Exception e) {
            System.err.println("Непредвиденная ошибка.");
        }
    }
}
