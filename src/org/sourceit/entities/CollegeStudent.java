package org.sourceit.entities;

import org.sourceit.properties.Gender;
import org.sourceit.properties.Subject;

public class CollegeStudent extends Student {

    protected int year;
    protected Subject major;

    public CollegeStudent() {
        this.year = 2008;
        this.major = Subject.CS;
    }

    public CollegeStudent(int year, Subject major) {
        this.year = year;
        this.major = major;
    }

    public CollegeStudent(String name, int age, Gender gender, String idNumber, double gpa, int year, Subject major) {
        super(name, age, gender, idNumber, gpa);
        this.year = year;
        this.major = major;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    @Override
    public Gender getGender() {
        return super.getGender();
    }

    @Override
    public void setGender(Gender gender) {
        super.setGender(gender);
    }

    @Override
    public String getIdNumber() {
        return super.getIdNumber();
    }

    @Override
    public void setIdNumber(String idNumber) {
        super.setIdNumber(idNumber);
    }

    @Override
    public double getGpa() {
        return super.getGpa();
    }

    @Override
    public void setGpa(double gpa) {
        super.setGpa(gpa);
    }

    @Override
    public String toString() {
        return "CollegeStudent{{" + super.toString() + "}" +
                "year=" + year +
                ", major=" + major +
                '}';
    }
}
