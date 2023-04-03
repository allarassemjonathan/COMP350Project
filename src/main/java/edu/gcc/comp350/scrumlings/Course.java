package edu.gcc.comp350.scrumlings;


import java.util.Arrays;

public class Course {
    // Member variables
    private String title;
    private String professor;

    private String[] date;
    private String dept;
    private int courseNum;
    private char section;
    private boolean fall;
    private boolean spring;
    private int credits;
    private int capacity;

    //Constructor
    public Course(String dept, int courseNum, char section, String title, boolean fall, int credits,
                  int capacity, String[] date, String professor) {
        this.dept = dept;
        this.courseNum = courseNum;
        this.section = section;
        this.title = title;
        this.fall = fall;
        this.spring = !fall;
        this.credits = credits;
        this.capacity = capacity;
        this.date = date;
        this.professor = professor;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public String getProfessor() {
        return professor;
    }

    public String[] getDate() {
        return date;
    }

    public String getDept() {
        return dept;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public char getSection() {
        return section;
    }

    public boolean isFall() {
        return fall;
    }
    public boolean isSpring() {
        return !isFall();
    }

    public int getCredits() {
        return credits;
    }

    public int getCapacity() {
        return capacity;
    }

    // To String method for a course
    @Override
    public String toString() {
        String result = dept + "|" + courseNum + "|" + section + "|" + title + "|";
        result+=" " + Arrays.toString(date);
        result += "|" + professor;
        return result;
    }
}