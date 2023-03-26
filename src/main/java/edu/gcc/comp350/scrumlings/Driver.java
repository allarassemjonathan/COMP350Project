package edu.gcc.comp350.scrumlings;

import java.io.FileNotFoundException;
import java.sql.PseudoColumnUsage;
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
            "a schedule you have created" + "\nsearch: allows you to search for a course";

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
    public static void main(String[] args) throws Exception {
        //test
        String[] timeOne = {"M 12"};
        String[] timeTwo = {"M 12"};
        String[] timeThree = {"W 12", "W 13","F 34"};
        Course testOne = new Course("Comp250", "wolfe", timeOne,"CS", 1, 'A');
        Course testTwo = new Course("Comp350", "wolfe", timeTwo,"CS", 1, 'A');
        Schedule s = new Schedule("Ava","");
        s.getCourses().add(testTwo);
        s.addCourse(testOne);



        // init
        System.out.println("Welcome to the Scrumlings Semester Scheduler!");
        System.out.println("Enter help for a list of commands");
        // main loop
        while(true) {
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
                Student myStudent = new Student(studentName, studentEmail, studentMajor, studentAdvisor, 00);

                System.out.println("Enter a name for your schedule:  ");
                String scheduleName = scnr.nextLine();
                Schedule mySchedule = new Schedule(scheduleName, "placeholder");
                System.out.println("Your schedule " + scheduleName + " has been created!");

                myStudent.addSchedule(mySchedule);
            }

            else if (userInput.equalsIgnoreCase("search")){
                System.out.println("If you would like to add a filter to your search, enter title, date, or code. " +
                        "Otherwise enter skip.");
                String type = scnr.nextLine();
                String filter;
                Search search = new Search();
                if (type.equals("skip")) {
                    System.out.println("No filters added.");
                }
                else if (type.equals("title")){
                    System.out.println("Enter a course title:");
                    filter = scnr.nextLine();
                    search.addFilter(type, filter);
                }
//                else if (type.equals("date")) {
//                    System.out.println("Enter the days of the week followed by the start and end time of your search");
//                    filter = scnr.nextLine();
//                    System.out.println(type + " " + filter);
//                }
//                else if (type.equals("code")) {
//                    System.out.println("Enter the course department and code of your search");
//                }
                else {
                    System.out.println("Please enter a valid command");
                }
            }

            else if (userInput.equalsIgnoreCase("automatic")){

            }
            else if (userInput.equalsIgnoreCase("delete")){

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