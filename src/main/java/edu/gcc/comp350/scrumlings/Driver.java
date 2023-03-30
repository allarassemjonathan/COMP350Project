package edu.gcc.comp350.scrumlings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
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
   // private Schedule schedule;
    private Generator generator;
    private Search search;


    private static Scanner scnr = new Scanner(System.in);
    private static String userInput = "";
    private static String help = "Write list of commands and what they do in this string," +
            "\nmanual: allows you to manually create a schedule" + "\ndelete: allows you to delete" +
            "a schedule you have created" + "\nsearch: allows you to search for a course"
            + "\ndisplay: allows you to display a schedule";

    public Driver(){

    }
    // Constructor
    public Driver(Student student, Schedule schedule, Generator generator, Search search) {
        this.student = student;
       // this.schedule = schedule;
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
    //public Schedule getSchedule() {
    //    return schedule;
    //}
   // public void setSchedule(Schedule schedule) {
    //    this.schedule = schedule;
   // }
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
    public static void main(String[] args) throws Exception {
        Schedule schedule = null;
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
              // Schedule mySchedule = new Schedule(scheduleName, "placeholder");
                schedule = new Schedule(scheduleName);
                System.out.println("Your schedule " + scheduleName + " has been created! To find classes, type search.");
                student.addSchedule(schedule);
                //System.out.println("schedule added");
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
//                else if (type.equals("date")) {
//                    System.out.println("Enter the days of the week followed by the start and end time of your search");
//                    filter = scnr.nextLine();
//                    System.out.println(type + " " + filter);
//                }
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
                    }
                    System.out.println(search.getFilters());
                    System.out.println("Would you like to add another filter? Y/N");
                    if (scnr.nextLine().equalsIgnoreCase("n")) {
                        break;
                    }
                }
               // search.setResultCourses(search.searchCourses((allCourses)));
                System.out.println("Enter the number of the class you wish to add");
                int courseNum = scnr.nextInt();
                Course found = search.getResultCourses().get(courseNum);
                //System.out.println(found.toString());
                schedule.addCourse(found);

                System.out.println("Your classes are now: ");
                for(Course r: schedule.getCourses()){
                    System.out.println(r.toString());
                }
                System.out.println("Enter a command to continue scheduling ");
            }


            else if (userInput.equalsIgnoreCase("automatic")){

            }
            else if (userInput.equalsIgnoreCase("delete")){
                System.out.println("Enter the name of the schedule to be deleted");
                String scheduleName = scnr.nextLine();
                student.removeSchedule(scheduleName);
                System.out.println("Your schedule has been removed");
            }
            else if (userInput.equalsIgnoreCase("display")){
                System.out.println("Enter the name of the schedule you wish to display");
                String scheduleName = scnr.nextLine();

                for(Schedule s : student.getSchedules()){
                    if(s.getTitle().equals(scheduleName)){
                        s.DisplaySchedule();
                    }

                }

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