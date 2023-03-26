package nsu.fit.tsukanov.pizzeria.modern;

import nsu.fit.tsukanov.pizzeria.modern.common.interfaces.PizzaService;
import nsu.fit.tsukanov.pizzeria.modern.controller.Pizzeria;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Pizzeria pizzeria = new Pizzeria();

        pizzeria.start();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            switch (line) {
                case "start" -> pizzeria.start();
                case "stop" -> pizzeria.stop();
                case "end" -> {
                    pizzeria.stop();
                    System.out.println("Ends");
                    return;
                }
            }
        }

    }



}
