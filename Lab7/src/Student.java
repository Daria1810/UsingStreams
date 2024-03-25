import java.util.*;
public class Student {
    private int studentID;
    private String name;
    private String group;
    private List<Course> courses;

    public Student(int studentID, String name, String group) {
        this.studentID = studentID;
        this.name = name;
        this.group = group;
    }

    public void addCourse(Course course) {
        if (courses == null) {
            courses = new ArrayList<>();
        }
        courses.add(course);
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public List<Course> getCourses() {
        return courses;
    }

    //I did NOT know this worked in java as well until I tried it!!!!
    @Override
    public String toString() {
        return String.format("Student ID: %d, Name: %s, Group: %s", studentID, name, group);
    }

}
