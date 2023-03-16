package edu.gcc.comp350.scrumlings;

import java.util.Date;

public class Course {
    // Member variables
    private String title;
    private String professor;
    private Date date;
    private String dept;
    private int courseNum;
    private char section;

    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public char getSection() {
        return section;
    }
    public void setSection(char section) {
        this.section = section;
    }
    public String getProfessor() {
        return professor;
    }
    public void setProfessor(String professor) {
        this.professor = professor;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getDept() {
        return dept;
    }
    public void setDept(String dept) {
        this.dept = dept;
    }
    public int getCourseNum() {
        return courseNum;
    }
    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    // Other Methods
    public boolean sameTime(Course otherCourse) {
        return true;
    }
}