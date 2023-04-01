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
        resultCourses = new ArrayList<>();
    }

    // Other Methods
    public void addFilter(String type, String filter) throws FileNotFoundException {
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
    public void removeFilter(String type, String filter) {
        if (filters.containsKey(type)) {
            if (filters.get(type).contains(filter)) {
                filters.get(type).remove(filter);
            }
        }
    }

    public ArrayList<Course> searchCourses(ArrayList<Course> allCourses) {
        ArrayList<Course> titles = new ArrayList<>();
        ArrayList<Course> codes = new ArrayList<>();
        ArrayList<Course> dates = new ArrayList<>();

        for (Course c: allCourses) {
            if (this.filters.containsKey("title")) {
                for (String title : filters.get("title")) {
                    if (c.getTitle().contains(title) && !titles.contains(c)) {
                        titles.add(c);
                    }
                }
            }
            else {
                titles = allCourses; //default: no filtering
            }

            if (this.filters.containsKey("code")) {
                for (String code : filters.get("code")) {
                    if (((c.getDept() + " " + c.getCourseNum()).contains(code)) && !codes.contains(c)) {
                         codes.add(c);
                    }
                }
            }
            else {
                codes = allCourses; //default: no filtering
            }

            if (this.filters.containsKey("date")) {
                for (String date : filters.get("date")) {
                    if (((c.getDate()[1].contains(date) || c.getDate()[2].contains(date))) && !dates.contains(c)) {
                        dates.add(c);
                    }
                }
            }
            else {
                dates = allCourses; //default: no filtering
            }

            if (titles.contains(c) && codes.contains(c) && dates.contains(c)) {
                resultCourses.add(c);
            }

        }
        return resultCourses;
    }
}