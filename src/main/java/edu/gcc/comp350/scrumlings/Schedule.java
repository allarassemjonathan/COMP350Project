package edu.gcc.comp350.scrumlings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


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
    public void addCourse(Course c) throws Exception {

        //compare course to current schedule's list of classes
        for (int i = 0; i < this.getCourses().size(); i++) {

            //{"M 12", "W 12", "F 12"}
           // String[] one = c.getDate();
           // String[] two = this.courses.get(i).getDate();

            //for each course in Schedule.course compare date to c.date
              boolean b = is_conflict(c.getDate(),this.courses.get(i).getDate());
           // System.out.println(b);
                if (b == false) {
                    System.out.println("Course has been added");
                }
                if(b) {
                    Scanner scnr = new Scanner(System.in);
                    String userInput = "";

                    // otherwise flag that there is a conflict
                    System.out.println("There is a time conflict");
                    System.out.println("You will need to choose between " + c.getTitle()
                            + " or " + this.courses.get(i).getTitle());

                    System.out.println("Press 1 for " + c.getTitle());
                    System.out.println("Press 2 to keep " + this.courses.get(i).getTitle());

                    userInput = scnr.nextLine();

                    //if scanner is 1 then add course and delete
                    if (userInput.equals("1")) {
                        this.courses.add(c);
                        this.courses.remove(i);
                        System.out.println("Course has been added");
                    }
                    //if scanner is 2 then continue
                    else if (userInput.equals("2")) {
                        System.out.println("You have kept the original course");
                    }

                }
            }
        }

      // ["M 12"]
      // ["M 12"]
      // returning false should be true

    public boolean is_conflict(String [] time_1, String [] time_2){
       // System.out.println(time_1.length);
       // System.out.println(time_2.length);
// otherwise compare the two dates
     for (int i = 0; i < time_1.length ; i++){
           // System.out.println(" This is comparing " + time_1[i] + " out of " + time_1);
           // System.out.println(time_1[i]);
            for (int j = 0; j < time_2.length; j++){
            //   System.out.println(" and this " + time_2[j] + " out of " + time_2);
              //  System.out.println(time_2[j]);
                if (time_1[i].equals(time_2[j])){
                    return true;
                }
            }
        }
        return false;
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }
    public void removeCourse(int index) {
        courses.remove(index);
    }
}