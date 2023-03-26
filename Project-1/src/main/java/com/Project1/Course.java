package com.Project1;

import org.springframework.stereotype.Component;

@Component
public class Course {
    private String courseNo;
    private String grade;
    private String creditHours;

    public Course() {
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
        this.creditHours = creditHours;
    }

    @Override
    public String toString() {
        return "{" +
                "courseNo='" + courseNo + '\'' +
                ", grade='" + grade + '\'' +
                ", creditHours='" + creditHours + '\'' +
                '}';
    }
}
