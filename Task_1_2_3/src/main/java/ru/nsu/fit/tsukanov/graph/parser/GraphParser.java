package ru.nsu.fit.tsukanov.graph.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import ru.nsu.fit.tsukanov.graph.core.Graph;



public abstract class GraphParser {
    /**
     * Parsing from file adjacency matrix
     *
     * @param file  the file from that will take vertices and edges
     * @param graph the graph in which vertices and edges will be placed
     * @throws FileNotFoundException if there is no file
     */
    public static void parseAdjacencyMatrix(File file, Graph<String, ?> graph) throws FileNotFoundException {
        InputStreamReader reader = new FileReader(file);
        Scanner scanner = new Scanner(reader);
        String columns = scanner.nextLine();
        Scanner columnScanner = new Scanner(columns);
        ArrayList<String> arrayList = new ArrayList<>();
        int count = 0;
        while (columnScanner.hasNext()) {
            String vert = columnScanner.next();
            graph.addVertex(vert);
            arrayList.add(vert);
            count++;
        }
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                String dist = scanner.next();
                if (!dist.equals("-")) {
                    graph.addEdge(arrayList.get(i), arrayList.get(j), Double.parseDouble(dist));
                }
            }
        }

    }

    /**
     * Parsing from file adjacency matrix
     *
     * @param file  the file from that will take vertices and edges
     * @param graph the graph in which vertices and edges will be placed
     * @throws FileNotFoundException if there is no file
     */
    public static void parseList(File file, Graph<String, ?> graph) throws FileNotFoundException {
        InputStreamReader reader = new FileReader(file);
        Scanner scanner = new Scanner(reader);
        while (scanner.hasNextLine()) {
            String startVert = scanner.next();
            String endVert = scanner.next();
            double weight = Double.parseDouble(scanner.next());
            graph.addVertex(startVert);
            graph.addVertex(endVert);
            graph.addEdge(startVert, endVert, weight);
        }
    }
}
