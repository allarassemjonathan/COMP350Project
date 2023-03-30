package edu.gcc.comp350.scrumlings;
import java.io.*;
import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Driver {
    // Member Variables
    private Student student;
    private Schedule schedule;
    private Generator generator;
    private Search search;
    private static Scanner scnr = new Scanner(System.in);
    private static String userInput = "";
    private static String help = "List of commands:" +
            "\nmanual: allows you to manually create a schedule" + "\ndelete: allows you to delete" +
            "a schedule you have created" + "\nsearch: allows you to search for a course"
            + "\ndisplay: allows you to display a schedule";

    private String[] majors = {
            "Mathematics", "Computer Science", "Electrical Engineering", "Mechanical Engineering",
            "Chemistry", "Biology", "Physics", "Political Science", "Data Science", "Philosophy",
            "Conservation Biology", "Exercise Science", "Computer Engineering", "Communication and Art",
            "Design and Innovation", "Musics", "Economy"
    };
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

    public String[] getMajors(){
        return this.majors;
    }

    /**
     * @return A student object created based on the answers
     * of the student to the questions. This line is run whenever
     * the student_info.txt file is empty.
     */
    public Student questionsForStudent(){
        System.out.println("Enter your name:  ");
        String studentName = scnr.nextLine();
        String studentMajor = null;
        boolean not_in = true;
        while(not_in){
            System.out.println("Enter your major:  ");
            studentMajor = scnr.nextLine();
            for(String major : this.majors){
                if (major.equalsIgnoreCase(studentMajor)){
                    not_in = false;
                    break;
                }
            }
        }

        String studentEmail = null;
        boolean not_email = true;
        boolean not_gcc = true;
        while(not_email || not_gcc){
            System.out.println("Enter your GCC email:  ");
            studentEmail = scnr.nextLine();
            for (int i  = 0; i < studentEmail.length(); i++){
                if (studentEmail.charAt(i) == '@'){
                    not_email = false;
                }
            }
            if (!not_email){
                String [] check = studentEmail.split("@");
                String extension = check[1];
                if(extension.equals("gcc.edu")){
                    not_gcc = false;
                }
            }
        }

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
                    String name = read.nextLine();
                    String major = read.nextLine();
                    String email = read.nextLine();
                    String advisor = read.nextLine();
                    int semester = read.nextInt();
                    student = new Student(name, major, email,advisor, semester);
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

    public void WriteFile(){
        String filename = "student_info.txt";
        String content = this.student.getName()
                + "\n" + this.student.getMajor()
                + "\n" + this.student.getEmail()
                + "\n" + this.student.getAdvisor()
                + "\n" + this.student.getSemester();

        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
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
        driver.WriteFile();
        System.out.println("Welcome" + " " + student.getName());
        System.out.println("Start by typing a command to do something\n" +
                "If you are confused just type the command \"help\" and it enter ");

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
            String[] date = new String[5];
            for (int i = 0; i < 5; i++) {
                if (!(courseData[i+9] == null)) {
                    date[i] = courseData[i+9] + " " + courseData[14];
                }
            }
            newCourse.setDate(date);
            newCourse.setProfessor(courseData[17] + " " + courseData[16]);
            allCourses.add(newCourse);
        }


        // main loop
        while(true) {
            userInput = scnr.nextLine();
            // quitting
            if (userInput.equalsIgnoreCase("quit")) {
                if (!driver.schedule.getSavedStatus()){
                    Scanner sc = new Scanner(System.in);
                    if(sc.next().equals("yes")){
                        //TODO save the scedule
                    }
                    else if(sc.next().equals("no")){
                        //TODO do not save
                    }
                    else{
                        System.out.println("Enter a correct answer");
                    }
                    System.out.println("Do you want to save your schedule first");

                }
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
                System.out.println("Your schedule " + scheduleName + " has been created! To find classes, type search.");
                student.addSchedule(mySchedule);
                System.out.println("schedule added");
            }

            else if (userInput.equalsIgnoreCase("search")){
                boolean searching = true;
                Search search = new Search();
                while (searching) {
                    System.out.println("If you would like to add a filter to your search, enter title, date, or code. " +
                            "Otherwise enter skip.");
                    String type = scnr.nextLine();
                    String filter;
                    if (type.equalsIgnoreCase("skip")) {
                        System.out.println("No filters added.");
                    }
                    else if (type.equalsIgnoreCase("title")){
                        System.out.println("Enter a course title:");
                        filter = scnr.nextLine().toUpperCase();
                        search.addFilter(type, filter);
                    }
                else if (type.equals("date")) {
                    System.out.println("Enter the first day of the week followed by the start time of your search");
                    filter = scnr.nextLine();
                    search.addFilter(type, filter);

                }
                    else if (type.equals("code")) {
                        System.out.println("Enter the course department and code of your search");
                        filter = scnr.nextLine().toUpperCase();
                        search.addFilter(type, filter);
                    }
                    else {
                        System.out.println("Please enter a valid search filter.");
                    }
                    search.setResultCourses(search.searchCourses(allCourses));
                    int result = 0;
                    for (Course c: search.getResultCourses()) {
                        System.out.println(result + ". " + c.toString());
                        result++;
                        //0. dept + number + section + title + date + professor
                        //1. etc
                    }
                    //System.out.println(search.getFilters());
                    System.out.println("Would you like to add another filter? Y/N");
                    if (scnr.nextLine().equalsIgnoreCase("n")) {
                        break;
                    }
                }

            }

            else if (userInput.equalsIgnoreCase("automatic")){

            }
            else if (userInput.equalsIgnoreCase("delete")){
                System.out.println("Enter the name of the schedule to be deleted");
                String scheduleName = scnr.nextLine();
                student.removeSchedule(scheduleName);
                System.out.println("Your schedule has been removed");
            }
            else if (userInput.equalsIgnoreCase("display")) {
                System.out.println("Here is the list of saved schedules:");
                //String scheduleName = scnr.nextLine();
                // read from the directory and print all the files's name
                String directoryPath = "schedules/";
                File directory = new File(directoryPath);
                File[] files = directory.listFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        System.out.println("[FILENAME] " +  file.getName());
                    }
                }
                System.out.println("Type the name of the schedule you want to load");
                String scheduleName = scnr.nextLine();
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