package nsu.fit.tsukanov.model.entity.attendance;

import java.util.Date;
import java.util.Objects;

public record Lesson(Date date) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(date, lesson.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}