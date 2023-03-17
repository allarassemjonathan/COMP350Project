package edu.gcc.comp350.scrumlings;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Driver {
    // Member Variables
    private Student student;
    private Schedule schedule;
    private Generator generator;
    private Search search;
    private static Scanner scnr = new Scanner(System.in);
    private static String userInput = "";
    private static String help = "Write list of commands and what they do in this string";

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
    private static String io() {
        userInput = scnr.nextLine();
        return userInput;
    }

    private static void saveSchedule(Schedule s) {
        if (s.getTitle() == null) {
            System.out.print("It looks like you don't have a title for this schedule yet, our" +
                    "system requires your schedule to have a title before it can be saved." +
                    "Please enter the title for your schedule now: ");
            s.setTitle(io());
        }
        File f = new File(System.getProperty("user.dir") + "/files/" + s.getTitle() + ".schedule");
        try {
            if (f.createNewFile()) {
                PrintWriter fw = new PrintWriter(f);
                fw.write(s.toString());
                fw.flush();
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Schedule \"" + s.getTitle() + "\" has been saved to files.");
    }

    public static void main(String[] args) {
        // init
        // main loop
        while (true) {
            userInput = scnr.nextLine();
            // quitting
            if (userInput.equalsIgnoreCase("quit")) {
                System.out.println("Are you sure you want to quit?");
                userInput = scnr.nextLine();
                if (userInput.equalsIgnoreCase("yes")) {
                    break;
                }
            }
            // helping
            else if (userInput.equalsIgnoreCase("help")) {
                System.out.println(help);
            }
            // add commands here with an else if (userInput.equalsIgnoreCase([COMMAND])) {}
            else if (userInput.equalsIgnoreCase("save schedule")) {
                saveSchedule(null);
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