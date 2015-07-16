package org.sourceit.entities;

import org.sourceit.properties.Gender;
import org.sourceit.properties.Subject;

public class CollegeStudent extends Student {

    protected int year;
    protected Subject major;

    protected final String properties = super.properties + ",Year,Major";

    public CollegeStudent() {
        this.year = -1;
        this.major = null;
    }

    public CollegeStudent(String name, int age, Gender gender, String idNumber, double gpa, int year, Subject major) {
        super(name, age, gender, idNumber, gpa);
        this.year = year;
        this.major = major;
    }

    @Override
    public String getProperties() {
        return properties;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Subject getMajor() {
        return major;
    }

    public void setMajor(Subject major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return super.toString() + "," + year +
                "," + major;
    }
}
