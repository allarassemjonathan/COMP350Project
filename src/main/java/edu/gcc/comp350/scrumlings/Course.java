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


    //Constructor
    public Course(String title, String professor, String [] date, String dept,
                  int courseNum, char section) {
        this.title = title;
        this.professor = professor;
        this.date = date;
        this.dept = dept;
        this.courseNum = courseNum;
        this.section = section;

    }

    public Course() {

    }

    @Override
    public String toString() {
        String dateString = date[0] + " " + date[1] + " " + date[2] + " " + date[3] + " " + date[4];
        return dept + " " + courseNum + " " + section + " " + title + " " + dateString + " " + professor;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public char getSection() {
        return section;
    }
    public void setSection(char section) {
        this.section = section;
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

    // Other Methods
    public boolean sameTime(Course otherCourse) {
        return true;
    }
}