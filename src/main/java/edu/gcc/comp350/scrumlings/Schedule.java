package edu.gcc.comp350.scrumlings;

import com.sun.jdi.IntegerValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


class Schedule {
    // Member Variables
    private ArrayList<Course> courses;
    private String title;

    private boolean savedStatus = false;

    // Constructor
    public Schedule(String title) {
        this.title = title;

        courses = new ArrayList<>();
    }

    // Getters and Setters
    public boolean getSavedStatus() {
        return savedStatus;
    }

    public String toString() {
        String res = "";
        for (Course c : this.getCourses()) {
            res += c.toString() + "\n";
        }
        return res;
    }

    public void setSavedStatus(boolean status) {
        savedStatus = status;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public Schedule(File f) {
        this.title = f.getName().substring(0, f.getName().length() - 9);
        courses = new ArrayList<>();
        try {
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                String courseString = reader.nextLine();
                List<String> info = Arrays.asList(courseString.split("\\|"))
                        .stream()
                        .map(s -> s.trim())
                        .collect(Collectors.toList());
                String[] times = info.get(4).replace("[", "").replace("]", "").split(",");
                for (int i = 0; i < times.length; i++)
                    times[i] = times[i].trim();
                Course c = new Course(
                        info.get(0),
                        Integer.parseInt(info.get(1)),
                        info.get(2).charAt(0),
                        info.get(3),
                        true,
                        0,
                        0,
                        times,
                        info.get(4)
                );
                System.out.println(c.toString());
                this.courses.add(c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String DisplaySchedule() {
        // Define a calendar (map <character, courses>)
        HashMap<Character, ArrayList<Course>> calendar = new HashMap<>();

        // Create empty slots with dates
        calendar.put('M', new ArrayList<>());
        calendar.put('T', new ArrayList<>());
        calendar.put('W', new ArrayList<>());
        calendar.put('R', new ArrayList<>());
        calendar.put('F', new ArrayList<>());

        // Fill in the calendar with actual classes
        System.out.println(" " + this.courses.size() + " classes in the schedule");
        for (Course course : this.courses) {
            String[] times = course.getDate();
            for (String time : times) {
                if (time.length() != 0) {
                    Character key = time.charAt(0);
                    if (calendar.containsKey(key)) {
                        ArrayList<Course> temp = calendar.get(key);
                        temp.add(course);
                        calendar.put(key, temp);
                    }
                }
            }
        }
        String out = " " + this.getTitle() + "\n";
        for (Character c : calendar.keySet()) {
            out += c + " ";
            ArrayList<Course> els = calendar.get(c);
            for (Course el : els) {
                out += el.getTitle() + "-";
            }
            out += "\n";
        }
        return out;
    }

    // Other Methods
    public void addCourse(Course c) throws Exception {
        //compare course to current schedule's list of classes
        System.out.println("Adding a course");
        if (courses.isEmpty()) {
            this.courses.add(c);
        } else {
            boolean b = true;
            int i;
            for (i = 0; i < this.getCourses().size(); i++) {
                //for each course in Schedule.course compare date to c.date
                b = is_conflict(c.getDate(), this.courses.get(i).getDate());
                if (b) {
                    break;
                }
            }

            if (!b) {
                this.courses.add(c);
                System.out.println("Course has been added");
            }
            if (b) {
                Scanner scnr = new Scanner(System.in);
                String userInput = "";

                // otherwise flag that there is a conflict
                System.out.println("There is a time conflict");
                System.out.println("You will need to choose between " + c.getTitle()
                        + " or " + this.courses.get(i).getTitle());

                System.out.println("Press 1 for " + c.getTitle());
                System.out.println("Press 2 to keep " + this.courses.get(i).getTitle());

                userInput = scnr.nextLine();

                //if scanner is 1 then add course and delete
                if (userInput.equals("1")) {
                    this.courses.remove(i);
                    this.courses.add(c);
                    System.out.println("Course has been added");
                }
                //if scanner is 2 then continue
                else if (userInput.equals("2")) {
                    System.out.println("You have kept the original course");
                }
            }
        }
        System.out.println(this.courses.size() + " added.");
    }

    public void removeCourse(Course c) throws Exception {
        //compare course to current schedule's list of classes
        if (courses.isEmpty()) {
            System.out.println("Your schedule is empty");
        }
        this.courses.remove(c);
    }

    // [M 10], [W 10] , [F 10]
    public boolean is_conflict(String[] time_1, String[] time_2) {
        // otherwise compare the two dates
        for (int i = 0; i < time_1.length; i++) {
            for (int j = 0; j < time_2.length; j++) {
                if (time_1[i].equals(time_2[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeCourse(int index) {
        courses.remove(index);
    }
}