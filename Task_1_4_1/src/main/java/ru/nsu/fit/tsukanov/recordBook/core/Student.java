package ru.nsu.fit.tsukanov.recordBook.core;

/**
 * Information about student (owner of recordBook).
 *
 * @see RecordBook
 */
public record Student(String surname, String name,
               String patronymic, String department,
               long group, String email) {

    /**
     * cool string representation about all student information.
     *
     * @return cool string representation about all student information
     */
    @Override
    public String toString() {
        String snp = surname() + " " + name().charAt(0) + ". "
                + patronymic().charAt(0) + ".";
        return snp + " " + group() + " " + department;
    }
}
