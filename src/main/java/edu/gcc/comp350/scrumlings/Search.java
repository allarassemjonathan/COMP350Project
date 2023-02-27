package edu.gcc.comp350.scrumlings;

import java.util.HashMap;
import java.util.List;

public class Search {
    // Member Variables
    private HashMap<String, List<String>> filters;
    private ArrayList<Course> resultCourses;

    // Getters and Setters
    public HashMap<String, List<String>> getFilters() {
        return filters;
    }
    public void setFilters(HashMap<String, List<String>> filters) {
        this.filters = filters;
    }
    public ArrayList<Course> getResultCourses() {
        return resultCourses;
    }
    public void setResultCourses(ArrayList<Course> resultCourses) {
        this.resultCourses = resultCourses;
    }

    // Constructor
    public Search() {
        this.filters = new HashMap<>();
    }

    // Other Methods
    public void addFilter(String type, String filter) {

    }
    public void removeFilter(String type, String filter) {

    }
}