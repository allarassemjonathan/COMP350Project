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

    public Student(){
        this.name = "empty";
    }
    // Getters and Setters
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getMajor() {
        return major;
    }
    public String getAdvisor() {
        return advisor;
    }
    public int getSemester() {
        return semester;
    }
    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    //add a schedule to student
    public void addSchedule(Schedule s){
        this.schedules.add(s);
    }

    //if a schedule exists then remove it from student
    public void removeSchedule(String str){
        if(this.schedules.size()>0){
            for(int i = 0; i < this.schedules.size(); i++){
                Schedule s = this.schedules.get(i);
                System.out.println(s.toString());
                if(str.equals(s.getTitle())){
                    this.schedules.remove(i);
                    System.out.println("Your schedule " + str + " has been deleted");
                }else{
                    System.out.println("Schedule " + str + " not found please try again");
                }
            }
        }
        else{
            System.out.println("Create a schedule first.\nFollow instructions at the top of the page if confused");
        }
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