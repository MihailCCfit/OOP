package ru.nsu.fit.tsukanov.recordBook.core;

public final class TablePrinter {
    public static final int tableSize = 75;

    private TablePrinter() {
    }
    private static String createBorder(Character symbol){
        return "#" + (symbol.toString()).repeat(tableSize-2) + "#";
    }

    public static String addBorder(String text, String borderLine) {
        return
                borderLine + "\n"
                        + text
                        + borderLine + "\n"
                ;
    }

    public static String tableInfoWithBorder(Semester semester) {
        return addBorder(tableInfoWithoutBorder(semester),
                "#" + "+".repeat(tableSize - 2) + "#");
    }

    public static String tableInfoWithoutBorder(Semester semester) {
        String shortInf = semester.shortInfo();
        shortInf = shortInf.substring(0, Math.min(tableSize - 6, shortInf.length()));
        String tableOfSubjects = "";
        for (Subject subject : semester.getSubjects()) {
            String subjInfo = subject.shortInfo();
            subjInfo = subjInfo.substring(0, Math.min(tableSize - 2, subjInfo.length()));
            tableOfSubjects += "#" + " ".repeat(4) + subjInfo +
                    " ".repeat(tableSize - (6 + subjInfo.length())) + "#\n";
        }
        return ""
                + "#" + " ".repeat(4) + shortInf + " ".repeat(tableSize - (6 + shortInf.length())) + "#\n"
                + "#" + " ".repeat(tableSize - 2) + "#\n"
                + "#" + "-".repeat(tableSize - 2) + "#\n"
                + tableOfSubjects +
                "";
    }
    public static String tableInfoWithBorder(RecordBook recordBook) {
        return addBorder(tableInfoWithoutBorder(recordBook),
                createBorder('#'));
    }

    public static String tableInfoWithoutBorder(RecordBook recordBook) {
        String shortInf = recordBook.shortInfo();
        shortInf = shortInf.substring(0, Math.min(tableSize - 6, shortInf.length()));
        String tableOfSubjects = "";

        return ""
                + "#" + " ".repeat(4) + shortInf + " ".repeat(tableSize - (6 + shortInf.length())) + "#\n"
                + "#" + " ".repeat(tableSize - 2) + "#\n"
                + "#" + "-".repeat(tableSize - 2) + "#\n"
                + tableOfSubjects +
                "";
    }
}
