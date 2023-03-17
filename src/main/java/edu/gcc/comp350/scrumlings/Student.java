package edu.gcc.comp350.scrumlings;

import java.util.ArrayList;

public class Student {
    // Member Variables
    private String name;
    private String email;
    private String major;
    private String advisor;
    private int semester;
    private ArrayList<Schedule> schedules;

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public String getAdvisor() {
        return advisor;
    }
    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }
    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }
    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }
    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(Schedule s){
        this.schedules.add(s);
    }

    // Constructor
    public Student(String name, String email, String major, String advisor, int semester) {
        this.name = name;
        this.email = email;
        this.major = major;
        this.advisor = advisor;
        this.semester = semester;
        this.schedules = new ArrayList<>();
    }
}