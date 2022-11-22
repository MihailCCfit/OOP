package ru.nsu.fit.tsukanov.recordBook.core;

import java.util.Objects;

/**
 * Information about student (owner of recordBook).
 *
 * @see RecordBook
 */
public final class Student {
    private String surname;
    private String name;
    private String department;
    private long group;
    private String email;

    /**
     * Construct student
     *
     * @param surname    the surname of student
     * @param name       the name of student
     * @param department the department where student studying
     * @param group      the student's group
     * @param mail       the student's mail
     */
    public Student(String surname, String name, String department, long group, String mail) {
        this.surname = surname;
        this.name = name;
        this.department = department;
        this.group = group;
        this.email = mail;
    }

    public String surname() {
        return surname;
    }

    public String name() {
        return name;
    }

    public String department() {
        return department;
    }

    public long group() {
        return group;
    }

    public String email() {
        return email;
    }


    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setGroup(long group) {
        this.group = group;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Check equality by surname, name, department, group, mail.
     *
     * @param obj that will be checked for equality with this
     * @return ture if objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Student) obj;
        return Objects.equals(surname(), that.surname) &&
                Objects.equals(this.name(), that.name) &&
                Objects.equals(this.department(), that.department) &&
                this.group() == that.group &&
                Objects.equals(this.email(), that.email);
    }

    /**
     * Calculate hashCode by surname, name, department, group, email.
     *
     * @return hashCode by surname, name, department, group, email.
     */
    @Override
    public int hashCode() {
        return Objects.hash(surname, name, department, group, email);
    }

    /**
     * cool string representation about all student information.
     *
     * @return cool string representation about all student information
     */
    @Override
    public String toString() {
        return "Student[" +
                "surname=" + surname + ", " +
                "name=" + name + ", " +
                "department=" + department + ", " +
                "group=" + group + ", " +
                "mail=" + email + ']';
    }
}
