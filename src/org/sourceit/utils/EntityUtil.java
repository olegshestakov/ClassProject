package org.sourceit.utils;

import org.sourceit.entities.CollegeStudent;
import org.sourceit.entities.Person;
import org.sourceit.entities.Student;
import org.sourceit.entities.Teacher;
import org.sourceit.properties.Gender;
import org.sourceit.properties.Subject;

public class EntityUtil {

    private static Person createPerson(String[] params) {

        Person person = new Person();

        commonPersonSet(params, person);

        return person;
    }

    private static Student createStudent(String[] params) {

        Student student = new Student();

        commonPersonSet(params, student);

        student.setIdNumber(params[3]);
        student.setGpa(Double.valueOf(params[4]));

        return student;

    }

    private static Teacher createTeacher(String[] params) {

        Teacher teacher = new Teacher();

        commonPersonSet(params, teacher);


        if (params.length == 5) {
            teacher.setSalary(Double.valueOf(params[3].trim()));
            switch (params[4].trim()) {
                case "CH":
                    teacher.setSubject(Subject.CH);
                    break;
                case "CS":
                    teacher.setSubject(Subject.CS);
                    break;
                case "EN":
                    teacher.setSubject(Subject.EN);
                    break;
                case "EE":
                    teacher.setSubject(Subject.EE);
                    break;
                case "CM":
                    teacher.setSubject(Subject.CM);
                    break;
            }
        }


        return teacher;
    }

    private static CollegeStudent createCollegeStudent(String[] params) {

        CollegeStudent collegeStudent = new CollegeStudent();

        commonPersonSet(params, collegeStudent);

        if (params.length == 7) {
            collegeStudent.setIdNumber(params[3]);
            collegeStudent.setGpa(Double.valueOf(params[4]));
            collegeStudent.setYear(Integer.valueOf(params[5]));
            switch (params[6].trim()) {
                case "CH":
                    collegeStudent.setMajor(Subject.CH);
                    break;
                case "CS":
                    collegeStudent.setMajor(Subject.CS);
                    break;
                case "EN":
                    collegeStudent.setMajor(Subject.EN);
                    break;
                case "EE":
                    collegeStudent.setMajor(Subject.EE);
                    break;
                case "CM":
                    collegeStudent.setMajor(Subject.CM);
                    break;
            }
        }

        return collegeStudent;
    }

    private static <T extends Person> void commonPersonSet(String[] params, T entity) {
        entity.setName(params[0].trim());
        entity.setAge(Integer.valueOf(params[1].trim()));

        switch (params[2].trim()) {
            case "male":
                entity.setGender(Gender.M);
                break;
            case "female":
                entity.setGender(Gender.F);
                break;
        }
    }

    public static <T extends Person> T createEntity(String[] params, T entity) {

        if (entity instanceof CollegeStudent) {
            return (T) createCollegeStudent(params);
        }
        if (entity instanceof Student) {
            return (T) createStudent(params);
        }
        if (entity instanceof Teacher) {
            return (T) createTeacher(params);
        }

        return (T) createPerson(params);
    }

}
