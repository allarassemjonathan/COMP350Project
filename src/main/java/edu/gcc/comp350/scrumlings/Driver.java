package edu.gcc.comp350.scrumlings;



import java.sql.Array;
import java.sql.SQLOutput;
import java.util.*;

public class Driver {
    // Member Variables
    private Student student;
    private Schedule schedule;
    private Generator generator;
    private Search search;
    public Driver(Schedule schedule){
        this.schedule =  schedule;
    }

    // Constructor
    public Driver(){

    }
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
        for (Course course : this.schedule.getCourses()){
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
    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.test_display();
    }

    // This is a test method to test the display schedule
    public void test_display(){
        String calendarString = "";
        Schedule schedule = new Schedule("test", "");

        // adding classes in schedule
        try{
            System.out.println("size " +schedule.getCourses().size());
            schedule.addCourse(new Course("MATH262","Dr.Thompson" , new String[]{"M 12", "W 12", "F 12"}));
            System.out.println("size " +schedule.getCourses().size());
            System.out.println("before");
            schedule.addCourse(new Course("COMP443", "Dr.Dickinson", new String[]{"T 4", "R 6"}));
            System.out.println("after");
            System.out.println("size " +schedule.getCourses().size());
            schedule.addCourse(new Course("COMP435", "Dr.Hutchins",  new String[]{"M 1", "W 1", "F 1"}));
            System.out.println("size " +schedule.getCourses().size());
            schedule.addCourse(new Course("COMP350", "Dr.Hutchins", new String[]{"T 5", "R 7"}));
            System.out.println("size " +schedule.getCourses().size());
            schedule.addCourse(new Course("COMP200", "Dr.Sow", new String[]{"T 5", "R 5"}));
            System.out.println("size " +schedule.getCourses().size());
            schedule.addCourse(new Course("COMP201", "Dr.Sow", new String[]{"T 5", "R 5"}));
            System.out.println("size " +schedule.getCourses().size());
        }
        catch(Exception e){
            System.out.println("size " +schedule.getCourses());
            System.out.println("bouya " + e.getMessage());
        }
        Driver driver = new Driver(schedule);
        System.out.println(driver.DisplaySchedule());
    }
    // Other Methods
//   public static void main(String[] args){
//        Driver driver = new Driver();
//        driver.DisplaySchedule();
//   }
}