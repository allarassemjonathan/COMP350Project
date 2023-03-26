package edu.gcc.comp350.scrumlings;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
//import org.junit.Assert;

class ScheduleTest {
    @Test public void addSameTime()throws Exception{
         String[] timeOne = {"M 12"};
         String[] timeTwo = {"M 12"};
        // String[] timeThree = {"W 12", "W 13","F 34"};
         Course testOne = new Course("Comp250", "wolfe", timeOne,"CS", 1, 'A');
         Course testTwo = new Course("Comp350", "wolfe", timeTwo,"CS", 1, 'A');
         Schedule s = new Schedule("Ava","");
         s.getCourses().add(testTwo);
         s.addCourse(testOne);
       // Assert.assertEquals();
    }

    @Test public void noConflict()throws Exception{
        String[] timeOne = {"M 12"};
        String[] timeTwo = {"M 12"};
        boolean b = true;
       // boolean check = is_conflict(timeOne,timeTwo);
       // Assert.assertEquals(check,b);
    }


}