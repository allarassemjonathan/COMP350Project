package edu.gcc.comp350.scrumlings;

import java.util.Arraylist;

class Schedule {
    // Member Variables
    private ArrayList<Courses> courses;
    private String title;
    private String path;

    // Constructor
    public Schedule(String title, String path) {
        this.title = title;
        this.path = path;
        courses = new ArrayList<>();
    }

    // Getters and Setters
    public ArrayList<Courses> getCourses() {
        return courses;
    }
    public void setCourses(ArrayList<Courses> courses) {
        this.courses = courses;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
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