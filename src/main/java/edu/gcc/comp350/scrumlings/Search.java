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
    public ArrayList<Course> addFilter(String type, String filter) throws FileNotFoundException {
        //1. Update the filters
        //adds new filter if type exists, otherwise adds new type
        if (filters.containsKey(type)) {
            filters.get(type).add(filter);
        }
        else {
            List<String> newFilterLst = new ArrayList<>();
            newFilterLst.add(filter);
            filters.put(type, newFilterLst);
        }

//
//        //2. Update the course list with new filters
//        //a. Search by title
        Scanner fileScn = new Scanner(new File("2020-2021.csv"));
        if (type.equals("title")) {
            while (fileScn.hasNext()) {
                String currCourse = fileScn.nextLine();
                String[] courseData = currCourse.split(",");
                if (courseData[5].equals(filter)) { //title entry matches filtered title
                  //  Course newCourse = new Course();
                  //  newCourse.setDept(courseData[2]);
                   // newCourse.setCourseNum(Integer.parseInt(courseData[3]));
                   // newCourse.setSection(courseData[4].charAt(0));
                   // newCourse.setTitle(courseData[5]);
                    String[] date = {courseData[9]+courseData[10]+courseData[11]+courseData[12]
                    +courseData[13], courseData[14], courseData[15]};
                   // newCourse.setDate(date);
                   // System.out.println(newCourse.getDept());
                   // System.out.println(newCourse.getCourseNum());
                   // System.out.println(newCourse.getSection());
                   // System.out.println(newCourse.getTitle());
                   // System.out.println(newCourse.getDate());
                   // resultCourses.add(newCourse);
                }
//
//
//
//
            }
//            for (Course course: resultCourses) {
//                System.out.println(course.getDept());
//                System.out.println(course.getCourseNum());
//                System.out.println(course.getSection());
//                System.out.println(course.getTitle());
//                System.out.println(course.getDate());
//            }
        }
        return resultCourses;

    }
    public void removeFilter(String type, String filter) {

    }
    public ArrayList<Course> searchCourses() {
        return null;
    }
}