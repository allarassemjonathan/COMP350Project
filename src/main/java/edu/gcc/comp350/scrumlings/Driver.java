package edu.gcc.comp350.scrumlings;

public class Driver {
    // Member Variables
    private Student student;
    private Schedule schedule;
    private Generator generator;
    private Search search;

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

    public String DisplaySchedule(){
        Course course_1 = new Course();
        return "";
    }

    // Other Methods
   public static void main(String[] args){

   }
}