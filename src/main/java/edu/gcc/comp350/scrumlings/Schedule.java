package edu.gcc.comp350.scrumlings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


class Schedule {
    // Member Variables
    private ArrayList<Course> courses;
    private String title;

    private boolean savedStatus = false;

    // Constructor
    public Schedule() {
        courses = new ArrayList<>();
    }

    public Schedule(String title) {
        this.title = title;

        courses = new ArrayList<>();
    }

    // Getters and Setters
    public boolean getSavedStatus() {
        return savedStatus;
    }

    public void setSavedStatus(boolean status) { savedStatus = status; }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public Schedule(File f) {
        this.title = f.getName().substring(0, f.getName().length() - 9);
        if (f.exists() && f.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                String fileContent = sb.toString().trim();
                // TODO parse courses into schedule
                Scanner inline = new Scanner(fileContent);
                while(inline.hasNext()){
                    String portion = inline.nextLine();
                    Character day = portion.charAt(0);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
        System.out.println(" " + this.courses.size() + " classes in the schedule");
        for (Course course : this.courses){
            String[] times = course.getDate();
            for (int i = 0; i < times.length; i++){
                if (course.getDate()[i].length()!=0){
                    Character key = course.getDate()[i].charAt(0);
                    ArrayList<Course> temp = calendar.get(key);
                    temp.add(course);
                    calendar.put(key, temp);
                }
            }
        }
        String out = "||";
        for (Character c: calendar.keySet()){
            out += c + " |";
            ArrayList<Course> els = calendar.get(c);
            for (Course el : els){
                out+= el.getTitle() + "|";
//                for (String part : el.getDate()){
//                    out += part + " ";
//                }
            }
            out += "|\n";
        }
        return out;
    }

    // Other Methods
    public void addCourse(Course c) throws Exception {
        //this.courses.add(c);
        // works with bug?
        //compare course to current schedule's list of classes
        System.out.println("Adding a course");
        if (courses.isEmpty()) {
            this.courses.add(c);
            System.out.println("empty adding");
        } else {
            //boolean conflict = true;
            boolean b = true;
            int i;
            for ( i = 0; i < this.getCourses().size(); i++) {
                //for each course in Schedule.course compare date to c.date
                b = is_conflict(c.getDate(), this.courses.get(i).getDate());
                // System.out.println(b);
                System.out.println("non-empty adding");
                if(b) {
                    break;
                }
            }

            if (!b) {
                this.courses.add(c);
                System.out.println("Course has been added");
            }
                if (b) {
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
                        this.courses.remove(i);
                        this.courses.add(c);
                        System.out.println("Course has been added");
                    }
                    //if scanner is 2 then continue
                    else if (userInput.equals("2")) {
                        System.out.println("You have kept the original course");
                    }
                }
            }
        System.out.println(this.courses.size() + " added.");
        }

    public void removeCourse(Course c) throws Exception {
        //this.courses.add(c);
        // works with bug?
        //compare course to current schedule's list of classes
        if (courses.isEmpty()) {
            System.out.println("Your schedule is empty");
        } else {
            //boolean conflict = true;
            boolean b = true;
            int i;
            for (i = 0; i < this.getCourses().size(); i++) {
                //for each course in Schedule.course compare date to c.date
                if (c.getTitle().equals(this.courses.get(i).getTitle())) {
                    courses.remove(c);
                }
            }

        }
    }



    // [M 10], [W 10] , [F 10]
    public boolean is_conflict(String [] time_1, String [] time_2){
       // System.out.println("Time 1: " + time_1.length);
       //System.out.println("Time 2: " + time_2.length);
       // otherwise compare the two dates
     for (int i = 0; i < time_1.length ; i++){
           // System.out.println(" This is comparing " + time_1[i] + " out of Time 1" );
            // System.out.println(time_1[i]);
            for (int j = 0; j < time_2.length; j++){
            //  System.out.println(" and this " + time_2[j] + " out of Time 2");
                 // System.out.println(time_2[j]);
                if (time_1[i].equals(time_2[j])){
                    return true;
                }
            }
        }
        return false;
    }

    public void removeCourse(int index) {
        courses.remove(index);
    }

}