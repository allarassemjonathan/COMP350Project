package edu.gcc.comp350.scrumlings;

import java.util.Scanner;

public class Driver {
    // Member Variables
    private Student student;
    private Schedule schedule;
    private Generator generator;
    private Search search;
    private static Scanner scnr = new Scanner(System.in);
    private static String userInput = "";
    private static String help = "Write list of commands and what they do in this string," +
            "\nmanual: allows you to manually create a schedule" + "\ndelete: allows you to delete" +
            "a schedule you have created";

    // Constructor
    public Driver(Student student, Schedule schedule, Generator generator, Search search) {
        this.student = student;
        this.schedule = schedule;
        this.generator = generator;
        this.search = search;
    }

    // Getters and Setters
    public Student getStudent() {
        return student;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Schedule getSchedule() {
        return schedule;
    }
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    public Generator getGenerator() {
        return generator;
    }
    public void setGenerator(Generator generator) {
        this.generator = generator;
    }
    public Search getSearch() {
        return search;
    }
    public void setSearch(Search search) {
        this.search = search;
    }


    // Other Methods
    public static void main(String[] args) {
        // init
        Student myStudent;
        System.out.println("Welcome to the Scrumlings Semester Scheduler!");

        //if there is no user file already
        System.out.println("Enter your name:  ");
        String studentName = scnr.nextLine();
        System.out.println("Enter your major:  ");
        String studentMajor = scnr.nextLine();
        System.out.println("Enter your email:  ");
        String studentEmail = scnr.nextLine();
        System.out.println("Enter your advisor:  ");
        String studentAdvisor = scnr.nextLine();
        System.out.println("Enter which semester your schedule is for:  ");
        String studentSemester = scnr.nextLine();
        myStudent = new Student(studentName, studentEmail, studentMajor, studentAdvisor, 00);
        System.out.println("Thank you for your info! To continue type manual or automatic");
        System.out.println("Enter help for a list of commands at anytime");

        // main loop
        while (true) {
            userInput = scnr.nextLine();
            // quitting
            if (userInput.equalsIgnoreCase("quit")) {
                System.out.println("Are you sure you want to quit?");
                userInput = scnr.nextLine();
                if(userInput.equalsIgnoreCase("yes")) {
                    break;
                }
            }
            // helping
            else if (userInput.equalsIgnoreCase("help")) {
                System.out.println(help);
            }
            // add commands here with an else if (userInput.equalsIgnoreCase([COMMAND])) {}
            else if (userInput.equalsIgnoreCase("manual")){
                System.out.println("You have selected to manually create a schedule");
                System.out.println("Enter a name for your schedule:  ");
                String scheduleName = scnr.nextLine();
                Schedule mySchedule = new Schedule(scheduleName);
                System.out.println("Your schedule " + scheduleName + " has been created!");

                myStudent.addSchedule(mySchedule);

                 for(Schedule s : myStudent.getSchedules()){
                    System.out.println(s.getTitle());
                }

            }
            else if (userInput.equalsIgnoreCase("automatic")){

            }
            else if (userInput.equalsIgnoreCase("delete")){
                System.out.println("You have selected to delete a schedule");
                System.out.println("Enter the name of the schedule you wish to delete:  ");
                String s = scnr.nextLine();
                myStudent.removeSchedule(s);
            }
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            else {
                System.out.println("Sorry, that command was not recognized. Type 'help' if you are " +
                        "confused to get a list of all valid commands and their function");
            }
        }
        // termination processes go here
        System.out.println("Closing program, thank you!");
    }
}