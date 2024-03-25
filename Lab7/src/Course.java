import java.util.*;

public class Course {
    private int courseId;
    private int creditPoints;
    private int year;
    private String fullName;
    private List<Student> students;

    public Course(int courseId, int creditPoints, int year, String fullName) {
        this.courseId = courseId;
        this.creditPoints = creditPoints;
        this.year = year;
        this.fullName = fullName;

    }

    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
    }

    public int getCourseID() {
        return courseId;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public int getYear() {
        return year;
    }

    public String getFullName() {
        return fullName;
    }

    public List<Student> getStudents() {
        return students;
    }

    // @Override
    // public int hashCode() {
    //     return fullName.hashCode();
    // }
}
