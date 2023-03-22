package edu.gcc.comp350.scrumlings;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Driver {
    // Member Variables
    private static Student student;
    private static Schedule schedule;
    private static Generator generator;
    private static Search search;
    private static Scanner scnr = new Scanner(System.in);
    private static String userInput = "";
    private static String help = "Write list of commands and what they do in this string";

    // Other Methods

    private static void saveSchedule() {
        if (schedule.getTitle() == null) {
            System.out.print("It looks like you don't have a title for this schedule yet, our\n" +
                    "system requires your schedule to have a title before it can be saved.\n" +
                    "Please enter the title for your schedule now: ");
            schedule.setTitle(scnr.nextLine());
        }
        File f = new File(System.getProperty("user.dir") + "/files/" + schedule.getTitle() + ".schedule");
        try {
            if (f.createNewFile()) {
                PrintWriter fw = new PrintWriter(f);
                fw.write(schedule.toString());
                fw.flush();
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Schedule \"" + schedule.getTitle() + "\" has been saved to files.");
    }

    private static Schedule importSchedule() {
        System.out.println("What is the name of your schedule?");
        String name = scnr.nextLine();
        File f = new File(System.getProperty("user.dir") + "/files/" + name + ".schedule");
        try {
            // if the schedule doesn't exist
            if (f.createNewFile()) {
                f.delete();
                System.out.println("It appears that file doesn't exist yet.");
                File[] schedules = new File(System.getProperty("user.dir") + "/files").listFiles();
                // if at least one schedules exists
                if (schedules.length > 0) {
                    String scheduleNames = "";
                    for (File s : schedules) {
                        if (s.getName().split("[.]")[1].equals("schedule")) {
                            scheduleNames += s.getName().split("[.]")[0] + "  ";
                        }
                    }
                    System.out.println("I was able to find the following schedules for you: \n" + scheduleNames);
                    System.out.println("Which of those would you like to import? Type 'Quit' to abort this action.");
                    while(true) {
                        userInput = scnr.nextLine();
                        // if user decides they don't want to import a schedule after all
                        if (userInput.equalsIgnoreCase("Quit")) {
                            return schedule;
                        }
                        // if user selects a schedule
                        else if (scheduleNames.contains(userInput)) {
                            f = new File(System.getProperty("user.dir") + "/files/" + userInput + ".schedule");
                            Scanner fr = new Scanner(f);
                            StringBuilder content = new StringBuilder();
                            while (fr.hasNext()) {
                                content.append(fr.nextLine());
                            }
                            fr.close();
                            return new Schedule(content);
                        } else {
                            System.out.println("I couldn't find that one, here are the schedules I found");
                            System.out.println(scheduleNames);
                        }
                    }
                } else {
                    System.out.println("It appears you have no files saved to our system," +
                            " you need to have a schedule created, and saved in order to use " +
                            "this features, would you like to create a schedule now?");
                    if (scnr.nextLine().equalsIgnoreCase("yes")) {
                        return new Schedule();
                    } else {
                        // TODO fix bug here
                        return schedule;
                    }
                }
            } else {
                Scanner fr = new Scanner(f);
                StringBuilder content = new StringBuilder();
                while (fr.hasNext()) {
                    content.append(fr.nextLine());
                }
                fr.close();
                return new Schedule(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedule;
    }

    public static void main(String[] args) {
        // init
        // main loop
        while (true) {
            System.out.print(">");
            userInput = scnr.nextLine();
            // quitting
            if (userInput.equalsIgnoreCase("quit")) {
                System.out.println("Are you sure you want to quit?");
                if (scnr.nextLine().equalsIgnoreCase("yes")) {
                    break;
                }
            }
            // helping
            else if (userInput.equalsIgnoreCase("help")) {
                System.out.println(help);
            }
            // add commands here with an else if (userInput.equalsIgnoreCase([COMMAND])) {}
            else if (userInput.equalsIgnoreCase("save schedule")) {
                saveSchedule();
            } else if (userInput.equalsIgnoreCase("import schedule")) {
                schedule = importSchedule();
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