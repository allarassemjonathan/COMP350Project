package edu.gcc.comp350.scrumlings;

import java.util.ArrayList;

class Schedule {
    // Member Variables
    private ArrayList<Course> courses;
    private String title;

    // Constructors
    public Schedule(String title) {
        this.title = title;
        courses = new ArrayList<>();
    }

    // builds schedule from file
    public Schedule(StringBuilder sb) {
        this.title = sb.toString();
    }

    public Schedule() {
        courses = new ArrayList<>();
    }

    // Getters and Setters
    public ArrayList<Course> getCourses() {
        return courses;
    }
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    // Other Methods
    public void addCourse(Course course) throws Exception {

    }
    public void removeCourse(Course course) {
        courses.remove(course);
    }
    public void removeCourse(int index) {
        courses.remove(index);
    }
}