package edu.gcc.comp350.scrumlings;


public class Course {
    // Member variables
    private String title;
    private String professor;

    //string[] ex = {"M 12", "W 12", "F 12"}

    private String[] date;
    private String dept;
    private int courseNum;
    private char section;
    private boolean fall;
    private boolean spring;
    private int credits;
    private int capacity;


    //Constructor
    public Course(String title, String professor, String [] date, String dept,
                  int courseNum, char section, boolean fall) {
        this.title = title;
        this.professor = professor;
        this.date = date;
        this.dept = dept;
        this.courseNum = courseNum;
        this.section = section;
        this.fall = fall;
        this.spring = !fall;
    }
    public Course(String dept, int courseNum, char section, String title, boolean fall, int credits,
                  int capacity, String[] date, String professor) {
        this.dept = dept;
        this.courseNum = courseNum;
        this.section = section;
        this.title = title;
        this.fall = fall;
        this.spring = !fall;
        this.credits = credits;
        this.capacity = capacity;
        this.date = date;
        this.professor = professor;
    }
    public Course() {

    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public boolean isFall() {
        return fall;
    }

    public void setFall(boolean fall) {
        this.fall = fall;
    }

    public boolean isSpring() {
        return spring;
    }

    public void setSpring(boolean spring) {
        this.spring = spring;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // Other Methods
    public boolean sameTime(Course otherCourse) {
        return true;
    }

    @Override
    public String toString() {
        String dateString = "";

        String result = dept + " " + courseNum + " " + section + " " + title + " " +
                date[0] + " " +date[1] + " " + date[2] + " " +date[3] + " " +date[4] + " " + professor;
        return result;
    }
}