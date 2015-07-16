package org.sourceit.entities;

import org.sourceit.properties.Gender;
import org.sourceit.properties.Subject;

public class Teacher extends Person {

    protected double salary;
    protected Subject subject;

    protected final String properties = super.properties + ",Salary,Subject";

    public Teacher() {
        this.salary = 0.00;
        this.subject = null;
    }

    public Teacher(String name, int age, Gender gender, Subject subject, double salary) {
        super(name, age, gender);
        this.salary = salary;
        this.subject = subject;
    }

    @Override
    public String getProperties() {
        return properties;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return super.toString() + "," + salary +
                "," + subject;
    }
}
