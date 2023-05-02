package ru.nsu.fit.tsukanov.graph;

import ru.nsu.fit.tsukanov.graph.alg.pathfinder.Dijkstra;
import ru.nsu.fit.tsukanov.graph.implementations.GraphIncList;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GraphIncList<Integer, Integer> graphIncList = new GraphIncList<>();
        Scanner scanner = new Scanner(System.in);
        var n = scanner.nextInt();
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];
        int[] arr3 = new int[n];
        int[] answ = new int[n];
        for (int i = 0; i < n; i++) {
            arr1[i] = scanner.nextInt();
        }
        for (int i = 0; i < n - 1; i++) {
            arr2[i] = scanner.nextInt();
        }
        for (int i = 0; i < n - 1; i++) {
            arr3[i] = scanner.nextInt();
        }

        for (int i = 0; i < n; i++) {
            graphIncList.addVertex(i);
        }
        for (int i = 0; i < n - 1; i++) {
            graphIncList.addEdge(i, arr2[i] - 1);
            graphIncList.addEdge(arr2[i] - 1, i);
        }
        for (int i = 0; i < n; i++) {
            Dijkstra<Integer, Integer> dijkstra = new Dijkstra<>(graphIncList, i);
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    if (dijkstra.getDistant(j) <= arr1[j]) {
                        answ[j] += 1;
                    }

                }
            }
        }
        for (int i : answ) {
            System.out.print(i + " ");
        }
    }
}
