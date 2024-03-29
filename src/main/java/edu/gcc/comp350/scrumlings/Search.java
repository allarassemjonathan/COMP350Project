package edu.gcc.comp350.scrumlings;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Search {
    // Member Variables
    private HashMap<String, List<String>> filters;
    private ArrayList<Course> resultCourses;

    // Getters and Setters
    public HashMap<String, List<String>> getFilters() {
        return filters;
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
        resultCourses = new ArrayList<>();
    }

    // Other Methods
    public void addFilter(String type, String filter) {
        //adds a new search filter to type title, code, or date
        if (filters.containsKey(type)) {
            filters.get(type).add(filter);
        }
        //adds type if it doesn't already exist, then adds filter to it
        else {
            List<String> newFilterLst = new ArrayList<>();
            newFilterLst.add(filter);
            filters.put(type, newFilterLst);
        }

    }
    public void removeFilters() {
        filters.clear();
    }

    public ArrayList<Course> searchCourses(ArrayList<Course> allCourses) {
        for (Course c: allCourses) {
            //filter by title
            if (this.filters.containsKey("title")) {
                for (String title : filters.get("title")) {
                    if (c.getTitle().contains(title) && !resultCourses.contains(c)) {
                        resultCourses.add(c);
                    }
                }
            }
            //filter by code
            if (this.filters.containsKey("code")) {
                for (String code : filters.get("code")) {
                    if (((c.getDept() + " " + c.getCourseNum() + " " + c.getSection()).contains(code)) && !resultCourses.contains(c)) {
                         resultCourses.add(c);
                    }
                }
            }
            //filter by date
            if (this.filters.containsKey("date")) {
                String[] fullDate = c.getDate();
                for (String date : filters.get("date")) {
                    if (fullDate[0].contains(date) && !resultCourses.contains(c)){
                        resultCourses.add(c);
                    }
                }
            }
        }
        return resultCourses;
    }
}