package edu.gcc.comp350.scrumlings;

import java.util.ArrayList;
import java.util.HashMap;

class Schedule {
    // Member Variables
    private ArrayList<Course> courses;
    private String title;

    // Constructor
    public Schedule(String title, String placeholder) {
        this.title = title;

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

    public String DisplaySchedule(){

        // Define a calendar (map <character, courses>)
        HashMap<Character, ArrayList<Course>> calendar = new HashMap<>();

        // Create empty slots with dates
        calendar.put('M', new ArrayList<>());
        calendar.put('T', new ArrayList<>());
        calendar.put('W', new ArrayList<>());
        calendar.put('R', new ArrayList<>());
        calendar.put('F', new ArrayList<>());

        // Fill in the calendar with actual classes
        for (Course course : this.getCourses()){
            String[] times = course.getDate();
            for (int i = 0; i < times.length; i++){
                Character key = course.getDate()[i].charAt(0);
                ArrayList<Course> temp = calendar.get(key);
                temp.add(course);
                calendar.put(key, temp);
            }
        }
        String out = "";
        for (Character c: calendar.keySet()){
            out += c + " " + calendar.get(c) +"\n";
        }
        return out;
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