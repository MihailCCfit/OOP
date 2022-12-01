package ru.nsu.fit.tsukanov.recordBook.core;

public final class TablePrinter {
    public static final int TABLE_SIZE = 100;
    public static final int DEFAULT_OFFSET = 4;

    private TablePrinter() {
    }

    private static String createBorder(Character symbol) {
        return inlineBorder(symbol.toString().repeat(TABLE_SIZE - 2));
    }

    private static String inlineBorder(String text) {
        String copiedText = text.substring(0, Math.min(text.length(), TABLE_SIZE - 2));
        return "#" + copiedText + offset(TABLE_SIZE -2-copiedText.length()) + "#";
    }

    public static String addBorder(String text, String borderLine) {
        return
                borderLine + "\n"
                        + text
                        + borderLine + "\n"
                ;
    }

    public static String tableInfoWithBorder(Semester semester) {
        return addBorder(semesterInfo(semester),
                "#" + "+".repeat(TABLE_SIZE - 2) + "#");
    }

    public static String semesterInfo(Semester semester) {
        String shortInf = semester.shortInfo();
        shortInf = shortInf.substring(0, Math.min(TABLE_SIZE - 6, shortInf.length()));
        String tableOfSubjects = "";
        for (Subject subject : semester.getSubjects()) {
            String subjInfo = subject.shortInfo();
            subjInfo = subjInfo.substring(0, Math.min(TABLE_SIZE - 2, subjInfo.length()));
            tableOfSubjects += "#" + " ".repeat(4) + subjInfo +
                    " ".repeat(TABLE_SIZE - (6 + subjInfo.length())) + "#\n";
        }
        return ""
                + createLine(shortInf)
                + createBorder('-') + "\n"
                + tableOfSubjects +
                "";
    }

    private static String offset(int i){
        return " ".repeat(i);
    }

    public static String recordBookInfo(RecordBook recordBook) {
        String shortAboutSemesters = "";
        String fullSemestersInfo = "";
        for (Semester semester : recordBook.getSemesters()) {
            shortAboutSemesters += inlineBorder(offset(4)+semester.shortInfo()) + "\n";
            fullSemestersInfo +=  createBorder('+') +"\n" + semesterInfo(semester);
        }


        return  createBorder('#') + "\n"
                + shortRecordBookInfo(recordBook)
                + createBorder('-') + "\n"
                + shortAboutSemesters
                + createLine()
                + fullSemestersInfo
                + createBorder('#')


                ;
    }
    private static String createLine(String text, int offset){
        return inlineBorder(offset(offset) + text) + "\n";
    }
    private static String createLine(String text){
        return createLine(text, DEFAULT_OFFSET);
    }
    private static String createLine(){
        return createLine("");
    }
    public static String shortRecordBookInfo(RecordBook recordBook){
        String qualMark = recordBook.getQualifyingMark()==0? "-" : String.format("%d",recordBook.getQualifyingMark());
        return
                createLine("RecordBook: ", 1)
                + createLine(recordBook.getStudent().toString())
                + createLine("Average mark: "+String.format("%.2f", recordBook.getAverage()))
                + createLine("High scholarship: " + (recordBook.hasHighScholarship()? "YES" : "NO"))
                + createLine("QualifyingMark: " + qualMark)
                + createLine("Red Diploma: " + (recordBook.hasRedDiploma()? "YES" : "NO"))
                + createLine("Semesters amount: " + recordBook.getSemesters().size());
    }
}
