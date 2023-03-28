package edu.gcc.comp350.scrumlings;
import java.io.*;
import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    // Member Variables
    private Student student;
    private Schedule schedule;
    private Generator generator;
    private Search search;
    private static ArrayList<Course> allCourses;
    private static Scanner scnr = new Scanner(System.in);
    private static String userInput = "";
    private static String help = "Write list of commands and what they do in this string," +
            "\nmanual: allows you to manually create a schedule" + "\ndelete: allows you to delete" +
            "a schedule you have created" + "\nsearch: allows you to search for a course";

    public Driver(){

    }
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

    private void saveSchedule() {
        if (schedule.getTitle() == null) {
            System.out.print("It looks like you don't have a title for this schedule yet, our" +
                    "system requires your schedule to have a title before it can be saved." +
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

    private Schedule importSchedule() {
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

    /**
     * @return A student object created based on the answers
     * of the student to the questions. This line is run whenever
     * the student_info.txt file is empty.
     */
    public Student questionsForStudent(){
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
        student = new Student(studentName, studentEmail, studentMajor, studentAdvisor, 00);
        return student;
    }
    public Student ReadFile(){
        File file = new File("student_info.txt");
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                String fileContent = sb.toString().trim();
                if (!fileContent.isEmpty()) {
                    Scanner read = new Scanner(fileContent);
                    student = new Student(read.nextLine(), read.nextLine(), read.nextLine(),read.nextLine(), read.nextInt());
                    return student;
                } else {
                    System.out.println("New user? Here are a couple of questions for you!\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found.");
        }
        student = new Student();
        return student;
    }

    // Other Methods
    public static void main(String[] args) throws FileNotFoundException {
        // root/init
        System.out.println("Scrumlings Semester Scheduler!");
        // if you have already used the app, it remembers you otherwise it ask for info
        Driver driver = new Driver();
        Student student = driver.ReadFile();
        if(student.getName().equals("empty")){
            student = driver.questionsForStudent();
        }
        System.out.println("Welcome" + " " + student.getName());
        System.out.printf("Here are the commands available at this point:\n1. help: get a list of commands" +
                "\n2. automatic : get the program to automatically set up the schedule for you" +
                "\n3. manual : set up the schedule yourself by searching for classes\n", "");

        ArrayList<Course> allCourses = new ArrayList<>();
        Scanner fileScn = new Scanner(new File("2020-2021.csv"));
        fileScn.nextLine();
        while (fileScn.hasNext()) {
            String currCourse = fileScn.nextLine();
            String[] courseData = currCourse.split(",");
            Course newCourse = new Course();
            newCourse.setDept(courseData[2]);
            newCourse.setCourseNum(Integer.parseInt(courseData[3]));
            if (courseData[4] != null && !courseData[4].isEmpty()) {
                newCourse.setSection(courseData[4].charAt(0));
            }
            newCourse.setTitle(courseData[5]);
            String[] date = {courseData[9] + courseData[10] + courseData[11] + courseData[12]
                    + courseData[13], courseData[14], courseData[15]};
            newCourse.setDate(date);
            allCourses.add(newCourse);
        }

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
                System.out.println(student.getName() + " you have selected to manually create a schedule");
                System.out.println("Enter a name for your schedule:  ");
                String scheduleName = scnr.nextLine();
                Schedule mySchedule = new Schedule(scheduleName, "placeholder");
                System.out.println("Your schedule " + scheduleName + " has been created!");
                student.addSchedule(mySchedule);
                System.out.println("schedule added");
            }
            // saving schedule to computer
            else if (userInput.equalsIgnoreCase("save schedule")) {
                driver.saveSchedule();
            }
            // importing schedule
            else if (userInput.equalsIgnoreCase("Import Schedule")) {
                driver.schedule = driver.importSchedule();
            }

            else if (userInput.equalsIgnoreCase("search")){
                System.out.println("If you would like to add a filter to your search, enter title, date, or code. " +
                        "Otherwise enter skip.");
                String type = scnr.nextLine();
                String filter;
                Search search = new Search();
                if (type.equalsIgnoreCase("skip")) {
                    System.out.println("No filters added.");
                }
                else if (type.equalsIgnoreCase("title")){
                    System.out.println("Enter a course title:");
                    filter = scnr.nextLine();
                    //search.addFilter(type, filter, allCourses);
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