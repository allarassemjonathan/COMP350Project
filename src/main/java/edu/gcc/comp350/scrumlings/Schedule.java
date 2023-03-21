package edu.gcc.comp350.scrumlings;

import java.util.ArrayList;


class Schedule {
    // Member Variables
    private ArrayList<Course> courses;
    private String title;
    private String path;

    // Constructor
    public Schedule(String title, String path) {
        this.title = title;
        this.path = path;
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
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public boolean is_conflict(String [] time_1, String [] time_2){
        if (time_1.length==0 || time_2.length==0){
            return false;
        }
        for (String time_11: time_1){
            System.out.println(" This is comparing " + time_11 + " out of " + time_1);
            for (String time_22 : time_2){
                System.out.println(" and this " + time_22 + " out of " + time_2);
                if (time_11.equals(time_22)){
                    return true;
                }
            }
        }
        return false;
    }
    // add to the schedule if there is no conflict
    public void addCourse(Course course) throws Exception {
        //TODO: this is my code AVA, feel free to completely remove it or just to debug
        //TODO: Currently the problem is that I can add even when there is a conmflict
        try{
            if(this.getCourses().size()==0){
                this.courses.add(course);
                System.out.println("course added");
            }
            else{
                for (Course clas: this.getCourses()){
                    if(!this.is_conflict(course.getDate(), clas.getDate())){
                        this.courses.add(course);
                        System.out.println("course added");
                    }
                    else{
                        System.out.println("Time conflict");
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Inside " + e.getMessage());
        }
    }
    public void removeCourse(Course course) {
        courses.remove(course);
    }
    public void removeCourse(int index) {
        courses.remove(index);
    }
}