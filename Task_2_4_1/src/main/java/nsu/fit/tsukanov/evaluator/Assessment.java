package nsu.fit.tsukanov.evaluator;

import lombok.Builder;

import java.util.Objects;

@Builder
public class Assessment {
    @Builder.Default
    private Double buildMark = .0;
    @Builder.Default
    private Double docsMark = .0;
    @Builder.Default
    private Double testMark = .0;
    @Builder.Default
    private Double extraScores = .0;

    public Assessment(Double buildMark, Double docsMark,
                      Double testMark, Double extraScores) {
        this.buildMark = buildMark;
        this.docsMark = docsMark;
        this.testMark = testMark;
        this.extraScores = extraScores;
    }

    public double getSummary() {
        return buildMark + docsMark + testMark + extraScores;
    }

    public Double compileMark() {
        return buildMark;
    }

    public Double docsMark() {
        return docsMark;
    }

    public Double testMark() {
        return testMark;
    }

    public Double extraScores() {
        return extraScores;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Assessment) obj;
        return Objects.equals(this.buildMark, that.buildMark) &&
                Objects.equals(this.docsMark, that.docsMark) &&
                Objects.equals(this.testMark, that.testMark) &&
                Objects.equals(this.extraScores, that.extraScores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildMark, docsMark, testMark, extraScores);
    }

    @Override
    public String toString() {
        return "Assessment[" +
                "compileMark=" + buildMark + ", " +
                "docsMark=" + docsMark + ", " +
                "testMark=" + testMark + ", " +
                "extraScores=" + extraScores + ']';
    }


}