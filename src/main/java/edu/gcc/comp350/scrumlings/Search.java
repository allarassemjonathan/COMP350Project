package edu.gcc.comp350.scrumlings;

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
    }

    // Other Methods
    public void addFilter(String type, String filter) {
        //1. Update the filters
        List<String> newFilter = getFilters().get(type);
        newFilter.add(filter);
        HashMap<String, List<String>> updated = getFilters();
        //adds new filter if type exists, otherwise adds new type
        if (updated.containsKey(type)) {
            updated.replace(type, newFilter);
        }
        else {
            updated.put(type, newFilter);
        }
        setFilters(updated);

        //2. Update the course list with new filters
        //a. Search by title
        Scanner fileScn = new Scanner("2020-2021.xlsx");
        ArrayList<Course> updatedCourses = getResultCourses();
        while (fileScn.hasNext()) {
            String currCourse = fileScn.nextLine();
            String[] courseData = currCourse.split(", ");
            if (courseData[5].equals(filter)) { //title entry matches filtered title
                Course newCourse = new Course();
                newCourse.setDept(courseData[2]);
                newCourse.setCourseNum(Integer.parseInt(courseData[3]));
                newCourse.setSection(courseData[4].charAt(0));
                newCourse.setTitle(courseData[5]);
                String[] dateString = null;
                for (int i = 0; i < 6; i++) {
                    if (courseData[i+9] != null) {
                        dateString[i]=courseData[i+9];
                    }

                }
                newCourse.setDate(dateString);

                updatedCourses.add(newCourse);
            }

        }

    }
    public void removeFilter(String type, String filter) {

    }
    public ArrayList<Course> searchCourses() {
        return null;
    }
}