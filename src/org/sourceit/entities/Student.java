package org.sourceit.entities;

import org.sourceit.properties.Gender;

public class Student extends Person {

    protected String idNumber;
    protected double gpa;

    protected final String properties = super.properties + ",Id,GPA";

    public Student() {
        this.idNumber = "000000";
        this.gpa = 0.0;
    }

    public Student(String name, int age, Gender gender, String idNumber, double gpa) {
        super(name, age, gender);
        this.idNumber = idNumber;
        this.gpa = gpa;
    }

    @Override
    public String getProperties() {
        return properties;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return super.toString() + "," + idNumber + "," + gpa;
    }
}
