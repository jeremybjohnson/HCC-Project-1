package com.Project1;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Student {
    private String firstName;
    private String email;
    private String gender;
    private List<Course> courses = new ArrayList<>();

    public Student() {
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void insertCourse(Course course) {
        this.getCourses().add(course);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "firstName: " + firstName + '\n' +
                "email: " + email + '\n' +
                "gender: " + gender + '\n' +
                "courses: " + courses;
    }
}
