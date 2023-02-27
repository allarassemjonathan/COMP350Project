package edu.gcc.comp350.scrumlings;
import java.util.ArrayList;
public class Department {
    // Member Variables
    private String title;
    private ArrayList<String> electives;

    // Constructors
    public Department(String title) {
        this.title = title;
        electives = new ArrayList<>();
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public ArrayList<String> getElectives() {
        return electives;
    }
    public void setElectives(ArrayList<String> electives) {
        this.electives = electives;
    }
}