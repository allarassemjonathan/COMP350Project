package edu.gcc.comp350.scrumlings;
import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Driver {
    // Member Variables
    private Student student;
    private Generator generator;
    private Search search;
    private final static ArrayList<Course> allCourses = init_courses();
    private static Scanner scnr = new Scanner(System.in);
    private static String userInput = "";
    private Schedule schedule;
    private static String help = "List of commands:" +
            "\nmanual: allows you to manually create a schedule" +
            "\ndelete: allows you to delete a schedule you have created" +
            "\nsearch: allows you to search for a course" +
            "\ndisplay: allows you to display a schedule" +
            "\nsave schedule: saves the schedule you are currently working on to your computer" +
            "\nimport schedule: allows you to pull up a schedule already on your computer.";
    private static String[] majors = {
            "Mathematics", "Computer Science", "Electrical Engineering", "Mechanical Engineering",
            "Chemistry", "Biology", "Physics", "Political Science", "Data Science", "Philosophy",
            "Conservation Biology", "Exercise Science", "Computer Engineering", "Communication and Art",
            "Design and Innovation", "Musics", "Economy"
    };
    public Driver(){

    }
    private static ArrayList<Course> init_courses() {
        ArrayList<Course> ret = new ArrayList<>();
        HashMap<String, ArrayList<String>> courses = new HashMap<>();
        try {
            File f = new File("./data/2020-2021.csv");
            Scanner fileScan = new Scanner(f);
            fileScan.nextLine();

            // keeps track of which index information after the title begins at
            int infoIndex;
            while(fileScan.hasNext()) {
                String[] info = fileScan.nextLine().split(",");
                /* Keeps track of all information related to the course besides its name
                0. semester: boolean (spring or fall)
                1. credits: int
                2. capacity: int
                3. time: String in the form "M: 10:00:00 AM - 10:50:00 AM, W: ..."
                4. professor: String in the form [FIRST NAME] [LAST NAME]
                 */
                ArrayList<String> courseInfo = new ArrayList<>();

                // name = [DEPT]|[CODE]|[SECTION]|[TITLE]
                String name = info[2] + "|" + info[3] + "|" + info[4];

                // parses title for courses with commas in the name (i.e "SCIENCE, FAITH, AND TECHNOLOGY")
                if(!info[5].contains("\"")) {
                    name += "|" + info[5];
                    infoIndex = 6;
                }
                else {
                    name += "|" + info[5].substring(1) + ",";
                    int i = 6;
                    while(true) {
                        if(info[i].contains("\"")) {
                            name += info[i].substring(0,info[i].length() - 1);
                            break;
                        } else {
                            name += info[i] + ",";
                        }
                        i++;
                    }
                    infoIndex = i + 1;
                }
                courseInfo.add(info[1]);
                courseInfo.add(info[infoIndex]);
                courseInfo.add(info[infoIndex + 1]);
                String time = "";
                for (int i = infoIndex + 3; i < infoIndex + 8; i++) {
                    time += info[i] + ((info[i].length() > 0) ? ": " + info[infoIndex + 8] + " - " + info[infoIndex + 9] + "," : "");
                }
                courseInfo.add((time.length() == 0) ? "TBD" : time.trim());
                courseInfo.add(info[infoIndex + 11] + " " + info[infoIndex + 10]);

                // merge courses that meet at separate times
                if(courses.containsKey(name)) {
                    String[] mergedInfo = {
                            courseInfo.get(0),
                            courseInfo.get(1),
                            courseInfo.get(2),
                            "", // placeholder for later
                            courseInfo.get(4)
                    };
                    if(courses.get(name).get(3).equals("TBD") && courseInfo.get(3).equals("TBD")) {
                        mergedInfo[3] = "TBD";
                    } else if (courses.get(name).get(3).equals("TBD")) {
                        mergedInfo[3] = courseInfo.get(3);
                    } else if (courseInfo.get(3).equals("TBD")) {
                        mergedInfo[3] = courses.get(name).get(3);
                    } else {
                        mergedInfo[3] = courses.get(name).get(3) + "," + courseInfo.get(3);
                    }
                    ArrayList<String> merge = new ArrayList<>();
                    for(String s : mergedInfo) { merge.add(s); }
                    courses.put(name, merge);
                }

                // add course to hashmap
                else {
                    courses.put(name, courseInfo);
                }
            }
            for (Map.Entry<String, ArrayList<String>> entry : courses.entrySet()) {
                String[] name = entry.getKey().split("\\|");
                ret.add(
                        new Course(
                                name[0],
                                Integer.parseInt(name[1]),
                                (name[2].length() == 0) ? '\0' : name[2].charAt(0),
                                name[3],
                                (Objects.equals(entry.getValue().get(0), "10")),
                                Integer.parseInt(entry.getValue().get(1)),
                                Integer.parseInt(entry.getValue().get(2)),
                                entry.getValue().get(3).split(","),
                                entry.getValue().get(4)
                        )
                );
            }
            return ret;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    //save a schedule to your computer
    private void saveSchedule() {
        if (this.schedule == null) {
            System.out.print("It looks like you don't have a title for this schedule yet, our\n" +
                    "system requires your schedule to have a title before it can be saved.\n" +
                    "Please enter the title for your schedule now: \n");
            this.schedule = new Schedule(scnr.nextLine());
        }
        File f = new File(System.getProperty("user.dir") + "/schedules/" + this.schedule.getTitle() + ".schedule");
        try {
            PrintWriter fw = new PrintWriter(f);
            for (Course c: this.schedule.getCourses()){
                fw.write(c.toString() + "\n");
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Schedule \"" + this.schedule.getTitle() + "\" has been saved to files.");
    }

    //import a schedule that has already been created
    private Schedule importSchedule() {
        File[] schedules = new File(System.getProperty("user.dir") + "/schedules").listFiles();
        if(schedules.length == 0) {
            System.out.println("It looks like you don't have any schedules to import!" +
                    "Try making one and saving it before using this feature.");
            return null;
        }
        String scheduleNames = "";
        for (File s : schedules) {
            if (s.getName().split("[.]")[1].equals("schedule")) {
                scheduleNames += s.getName().split("[.]")[0] + "  ";
            }
        }
        //list names of found schedules
        while(true) {
            System.out.println("Here are the schedules I found on your computer," +
                    "\n" + scheduleNames +
                    "\nWhich on would you like to import?");
            String name = scnr.nextLine();
            File f = new File(System.getProperty("user.dir") + "/Schedules/" + name + ".schedule");
            try {
                if (f.createNewFile()) {
                    f.delete();
                    System.out.println("It appears that file doesn't exist. Try again or enter \"Quit\" to abort this action.");
                } else if (scheduleNames.contains(name)) {
                    f = new File(System.getProperty("user.dir") + "/schedules/" + name + ".schedule");
                    return new Schedule(f);
                } else if (name.equalsIgnoreCase("quit")) {
                    return null;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * @return A student object created based on the answers
     * of the student to the questions. This line is run whenever
     * the student_info.txt file is empty.
     */
    public Student questionsForStudent() {
        System.out.println("Enter your name:  ");
        String studentName = scnr.nextLine();
        String studentMajor = null;
        boolean not_in = true;
        while(not_in){
            System.out.println("Enter your major:  ");
            studentMajor = scnr.nextLine();
            for(String major : this.majors){
                if (major.equalsIgnoreCase(studentMajor)){
                    not_in = false;
                    break;
                }
            }
        }

        String studentEmail = null;
        boolean not_email = true;
        boolean not_gcc = true;
        while(not_email || not_gcc){
            System.out.println("Enter your GCC email:  ");
            studentEmail = scnr.nextLine();
            for (int i  = 0; i < studentEmail.length(); i++){
                if (studentEmail.charAt(i) == '@'){
                    not_email = false;
                }
            }
            if (!not_email){
                String [] check = studentEmail.split("@");
                String extension = check[1];
                if(extension.equals("gcc.edu")){
                    not_gcc = false;
                }
            }
        }

        System.out.println("Enter your advisor:  ");
        String studentAdvisor = scnr.nextLine();
        System.out.println("Enter which semester your schedule is for:  ");
        String studentSemester = scnr.nextLine();
        student = new Student(studentName, studentEmail, studentMajor, studentAdvisor, 00);
        return student;
    }
    public Student ReadFile(){
        File file = new File("student_info.txt");
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                String fileContent = sb.toString().trim();
                if (!fileContent.isEmpty()) {
                    Scanner read = new Scanner(fileContent);
                    String name = read.nextLine();
                    String major = read.nextLine();
                    String email = read.nextLine();
                    String advisor = read.nextLine();
                    int semester = read.nextInt();
                    student = new Student(name, major, email,advisor, semester);
                    return student;
                } else {
                    System.out.println("New user? Here are a couple of questions for you!\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found.");
        }
        student = new Student();
        return student;
    }
    public void WriteFile() {
        String filename = "student_info.txt";
        String content = this.student.getName()
                + "\n" + this.student.getMajor()
                + "\n" + this.student.getEmail()
                + "\n" + this.student.getAdvisor()
                + "\n" + this.student.getSemester();

        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
    public void saveFile(){
        String filename = "schedules/" + this.schedule.getTitle() + ".schedule";
        String content = this.schedule.DisplaySchedule();
        if(this.schedule.getTitle()==null){
            System.out.println("No schedule to save.");
            return;
        }
        try {
            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);
            bufferedWriter.close();

        }catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
        System.out.println("saved");
        schedule.setSavedStatus(true);
    }
    // Other Methods
    public static void main(String[] args) throws Exception {
        // root/init
        System.out.println("Scrumlings Semester Scheduler!");
        // if you have already used the app, it remembers you otherwise it ask for info
        Driver driver = new Driver();
        //driver.schedule = new Schedule();
        Student student = driver.ReadFile();
        if(student.getName().equals("empty")){
            student = driver.questionsForStudent();
        }
        driver.WriteFile();
        System.out.println("Welcome" + " " + student.getName());
        System.out.println("Start by typing a command to do something\n" +
                "If you are confused just type the command \"help\" and it enter ");

        // main logic loop
        while(true) {
            userInput = scnr.nextLine();
            // quitting
            if (userInput.equalsIgnoreCase("quit")) {
                if (!driver.schedule.getSavedStatus()){
                    Scanner sc = new Scanner(System.in);
                    System.out.println("Would you like to save your schedule before?");
                    String response = sc.nextLine();
                    if(response.equals("yes")){
                        driver.saveFile();
                        System.out.println("quitting...");
                        return;
                    }
                    else if(response.equals("no")){
                        System.out.println("quitting...");
                        return;
                    }
                    else{
                        System.out.println("Enter a correct answer");
                    }
                }
                System.out.println("Are you sure you want to quit?");
                userInput = scnr.nextLine();
                if(userInput.equalsIgnoreCase("yes")) {
                    break;
                }
                else{
                    System.out.println("Enter a command to keep planning");
                }
            }
            // helping
            else if (userInput.equalsIgnoreCase("help")) {
                System.out.println(help);
            }
            // add commands here with an else if (userInput.equalsIgnoreCase([COMMAND])) {}
            else if (userInput.equalsIgnoreCase("manual")){
                System.out.println(student.getName() + " you have selected to manually create a schedule");
                System.out.println("Enter a name for your schedule or -1 to quit this section:  ");
                String scheduleName = scnr.nextLine();
                if(!scheduleName.equalsIgnoreCase("-1")) {

                    // adding the schedule to the student
                    System.out.println("A schedule has been created..");
                    driver.schedule = new Schedule(scheduleName);
                    ArrayList<Schedule> schedules = new ArrayList<>();
                    schedules.add(driver.schedule);
                   // student.setSchedules(schedules);
                    System.out.println("Your schedule " + scheduleName + " has been created! To find classes, type search.");
                    student.addSchedule(driver.schedule);
                }
                else{
                    System.out.println("Enter a command to plan something");
                }
            }
            // saving schedule to computer
            else if (userInput.equalsIgnoreCase("save schedule")) {
                driver.saveSchedule();
                System.out.println("Schedule saved");
            }
            // importing schedule
            else if (userInput.equalsIgnoreCase("Import Schedule")) {
                driver.schedule = driver.importSchedule();
            }
            //search for a course
            else if (userInput.equalsIgnoreCase("search")){
                boolean searching = true;
                Search search = new Search();
                int result = 0;
                while (searching) {
                    search.removeFilters();
                    System.out.println("If you would like to add a filter to your search, enter title, date, or code. " +
                            "Otherwise enter skip.");
                    String type = scnr.nextLine();
                    String filter = "";
                    //can search by title, date, code
                    while(!type.equalsIgnoreCase("skip")
                            && !type.equalsIgnoreCase("title")
                            && !type.equals("date")
                            && !type.equals("code")){
                        System.out.println("Give a legal filter");
                        type = scnr.nextLine();
                    }
                    if (type.equalsIgnoreCase("skip")) {
                        System.out.println("No filters added.");
                    }
                    else if (type.equalsIgnoreCase("title")){
                        System.out.println("Enter a course title:");
                        filter = scnr.nextLine().toUpperCase();
                        search.addFilter(type, filter);
                    }
                    else if (type.equals("date")) {
                        //enter MTWRF to search by day
                        while (!(filter.equalsIgnoreCase("M") ||
                                filter.equalsIgnoreCase("T") ||
                                filter.equalsIgnoreCase("W") ||
                                filter.equalsIgnoreCase("R") ||
                                filter.equalsIgnoreCase("F"))) {
                            filter = "";
                            System.out.println("Enter M,W,T,R, or F:");
                            filter += scnr.nextLine().charAt(0);
                        }
                        filter += ": ";
                        System.out.println("Enter a start time:");
                        filter += scnr.nextLine();
                        search.addFilter(type, filter);

                    }
                    else if (type.equals("code")) {
                        System.out.println("Enter the course department and code of your search");
                        filter = scnr.nextLine().toUpperCase();
                        search.addFilter(type, filter);
                    }

                    search.setResultCourses(search.searchCourses(allCourses));

                    //print list of all courses found in search
                    for (Course c: search.getResultCourses()) {
                        System.out.println(result + ". " + c.toString());
                        result++;
                        //0. dept + number + section + title + date + professor
                    }
                    //System.out.println(search.getFilters());
                    System.out.println("Would you like to change the filter? Y/N");
                    if (scnr.nextLine().equalsIgnoreCase("n")) {
                        break;
                    }
                }
                //enter the number of the course to add it to your schedule
                if(search.getResultCourses().size()>0 && driver.schedule!=null){
                        System.out.println("Enter the number of the class you wish to add or -1 not to");
                        int courseNum;
                        try{
                            courseNum = scnr.nextInt();
                            if(courseNum!=-1){
                                if (courseNum<0 || courseNum>search.getResultCourses().size()){
                                    throw new Exception("Give a number in the list");
                                }
                                Course found = search.getResultCourses().get(courseNum);
                                driver.schedule.addCourse(found);
                                System.out.println("Your classes are now: ");
                                for(Course r: driver.schedule.getCourses()){
                                    System.out.println(r.toString());
                                }

                            }
                        } catch (InputMismatchException e){
                            System.out.println("Give a number not a string");
                        }
                    }
                else{
                    System.out.println("No result found.");
                }
                System.out.println("Enter a command to continue planning");

                }
            else if (userInput.equalsIgnoreCase("automatic")){

            }
            //delete a created schedule from student
            else if (userInput.equalsIgnoreCase("delete")){
                System.out.println("Your schedules are: ");
                for(Schedule s : student.getSchedules()){
                    System.out.println(s.getTitle());
                }
                System.out.println("Enter the name of the schedule to be deleted");
                String scheduleName = scnr.nextLine();
                student.removeSchedule(scheduleName);
                System.out.println("Your remaining schedules are:");
                for(Schedule e : student.getSchedules()){
                    System.out.println(e.getTitle());
                }
            }
            else if (userInput.equalsIgnoreCase("remove")){
                System.out.println("Enter number next to the course you wish to delete");
                int i = 0;
                for(Course r: driver.schedule.getCourses()){
                    System.out.println(i + ". " + r.toString());
                    i++;
                }
                int number = scnr.nextInt();
                driver.schedule.removeCourse(number);
                System.out.println("Your course has been delete");
                System.out.println("Here is your updated schedule: ");
                for(Course r: driver.schedule.getCourses()){
                    System.out.println(r.toString());
                }
            }

            else if (userInput.equalsIgnoreCase("display")) {
                Scanner sc = new Scanner(System.in);
                System.out.println("Do you want to visualized current schedule?");
                if(sc.next().equalsIgnoreCase("yes")){
                    if(driver.schedule!=null){
                        System.out.println(driver.schedule.DisplaySchedule());
                    }
                    else{
                        System.out.println("No schedule created\nFollow instructions at the top of the page if confused");
                    }
                }
                else{
                    System.out.println("Here is the list of saved schedules:");
                    // read from the directory and print all the file's name
                    String directoryPath = "schedules/";
                    File directory = new File(directoryPath);
                    File[] files = directory.listFiles();
                    for (File file : files) {
                        if (file.isFile()) {
                            System.out.println("[FILENAME] " +  file.getName());
                        }
                    }
                    System.out.println("Type the name of the schedule you want to load");
                }
            }
            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            else {
                System.out.println("Sorry, that command was not recognized. Type 'help' if you are " +
                        "confused to get a list of all valid commands and their function");
            }
        }
        // termination processes go here
        System.out.println("Closing program, thank you!");
    }

}