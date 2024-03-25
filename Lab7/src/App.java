import java.util.*;
import java.util.stream.*;

// Student has a list of courses and course has a list of students.

// It is possible to write helper methods, to be used inside the pipeline.

// 1)      Receive a list of integers, remove odd elements, square each remaining element, collect the result in a new list. (hint use filter and map)

// 2)      Receive a list of integers, return the sum of squares (hint use map and reduce)

// 3)      Receive a list of Strings, return a map that has as key the string length and as value the number of strings with that length. Provide 2 solutions (exercise3a, exercise3b)

// 4)      Receive a list of lists of Strings, return a list with all the distinct words. (hint use flatmap)

// 5)      Receive a list of students and a group. Retain only the students from the given group, return a string containing their comma-separated names ( hint use Collectors.joining)

// 6)      Generate a large list of integers (over 1.000.000). Compute the sum of their squares. Compare the execution time of using parallel streams and sequential streams.

// 7)       Receive a list of students. Return the list of students enrolled in courses that have over 5 credit points.

// 8)      Receive a list of students. Return the list of students enrolled in courses that in total have over 30 credit points.

// 9)      Receive a list of courses. Return a map where the key is the course id and the value is the list of ids of the students enrolled in that course.

// 10)   Receive a list of students and a group. Return the list of the first 5 students in the group, sorted by name.

// 11)   Receive a list of students. Return a map where the key is the group and the value is the list of courses in which the students from the group are enrolled, with duplicates removed.



public class App {

    // Exercise 1

    public List<Integer> exercise1(List<Integer> numbers) {
        return numbers.stream() // Stream<Integer> numbers
                .filter(n -> n % 2 == 0) // filters the stream to only contain even numbers
                .map(n -> n * n) // transforms the stream to contain the squared values of the old stream
                .collect(Collectors.toList()); // collects the stream into a list
    }

    // Exercise 2
    public int exercise2(List<Integer> numbers) {
        return numbers.stream() // Stream<Integer> numbers
                .map(n -> n * n) // transforms the stream to contain the squared values of the old stream
                .reduce(0, Integer::sum); // reduces the stream to a single value by summing all the values
    }

    // Exercise 3a
    public Map<Integer, Long> exercise3a(List<String> strings) { // the map is <integer, long> because the
                                                                 // collector.counting() returns a long value
        return strings.stream() // Stream<String> strings
                .collect(Collectors.groupingBy(String::length, Collectors.counting())); // groups the stream by the
                                                                                        // length of the strings and
                                                                                        // counts the number of strings
                                                                                        // in each group
    }

    // Exercise 3b
    public Map<Integer, Integer> exercise3b(List<String> strings) {
        return strings.stream() // Stream<String> strings
                .collect(Collectors.groupingBy(String::length, Collectors.summingInt(s -> 1))); // groups the stream by
                                                                                                // the length of the
                                                                                                // strings and sums the
                                                                                                // number of strings in
                                                                                                // each group
    } // the map is <integer, integer> because the collector.summingInt() returns an
      // integer value

    // Exercise 4
    public List<String> exercise4(List<List<String>> listOfLists) {
        return listOfLists.stream() // Stream<List<String>> listOfLists
                .flatMap(List::stream) // flattens the stream of lists into a stream of strings (so basically it takes
                                       // the strings from each list and puts them into a single stream)
                .distinct() // removes the duplicates from the stream
                .collect(Collectors.toList()); // collects the stream into a list
    }

    // Exercise 5
    public String exercise5(List<Student> students, String group) {
        return students.stream() // Stream<Student> students
                .filter(s -> s.getGroup().equals(group)) // filters the stream to only contain students from the
                                                         // specified group
                .map(Student::getName) // transforms the stream to contain only the names of the students
                .collect(Collectors.joining(", ")); // collects the stream into a string, separating the names with a
                                                    // comma and a space
    }

    // Exercise 6
    public long exercise6P() {
        List<Integer> numbers = new ArrayList<>();
        // Generate a large list of integers
        for (int i = 0; i < 1000000; i++) {
            numbers.add(i);
        }

        // Parallel Stream
        long parallelSum = numbers.parallelStream() // Stream<Integer> numbers
                .mapToLong(n -> n * n) // transforms the stream to contain the squared values of the old stream
                .sum(); // reduces the stream to a single value by summing all the values

        return parallelSum;
    }

    public long exercise6S() {
        List<Integer> numbers = new ArrayList<>();
        // Generate a large list of integers
        for (int i = 0; i < 1000000; i++)
            numbers.add(i);

        // Sequential Stream
        long sequentialSum = numbers.stream() // Stream<Integer> numbers
                .mapToLong(n -> n * n) // transforms the stream to contain the squared values of the old stream
                .sum(); // reduces the stream to a single value by summing all the values

        return sequentialSum;
    }

    // Exercise 7
    public List<Student> exercise7(List<Student> students) {
        return students.stream()
                .filter(s -> s.getCourses().stream() // Stream<Course> courses
                        .anyMatch(c -> c.getCreditPoints() > 5)) // checks if any of the courses has more than 5 credit
                                                                 // points, we need the anyMatch() method because we
                                                                 // need to check if any of the courses has more than 5
                                                                 // credit points, not all of them
                .collect(Collectors.toList()); // collects the stream into a list
    }

    // Exercise 8
    public List<Student> exercise8(List<Student> students) {
        return students.stream()
                .filter(s -> s.getCourses().stream()
                        .mapToInt(Course::getCreditPoints)
                        .sum() > 30) // checks if the sum of the credit points of all the courses of a student is
                                     // greater than 30
                .collect(Collectors.toList()); // collects the stream into a list
    }

    // Exercise 9
    public Map<Integer, List<Integer>> exercise9(List<Course> courses) {
        Map<Integer, List<Integer>> courseStudentMap = new HashMap<>();

        for (Course course : courses) { // for each course
            int courseID = course.getCourseID(); // get the course ID
            List<Integer> studentIDs = course.getStudents().stream() // Stream<Student> students
                    .map(Student::getStudentID) // transforms the stream to contain only the student IDs
                    .collect(Collectors.toList()); // collects the stream into a list

            courseStudentMap.put(courseID, studentIDs); // put the course ID and the list of student IDs in the map
        }

        return courseStudentMap;
    }

    // Exercise 10
    public List<Student> exercise10(List<Student> students, String group) {
        return students.stream()
                .filter(s -> s.getGroup().equals(group)) // filter students by group
                .sorted(Comparator.comparing(Student::getName)) // sort students by name
                .limit(5) // limit the result to the first 5 students
                .collect(Collectors.toList()); // collect the result into a list
            
    }

    // Exercise 11
    public Map<String, List<String>> exercise11(List<Student> students) {
        Map<String, List<String>> groupCourseMap = new HashMap<>();

        for (Student student : students) {
            String group = student.getGroup(); // get the group of the student

            List<String> studentCourses = groupCourseMap.getOrDefault(group, new ArrayList<>()); // get the list of courses for the group, or create a new empty list if it doesn't exist

            List<String> courses = student.getCourses().stream() // Stream<Course> courses
                    .map(Course::getFullName) // transforms the stream to contain only the full names of the courses
                    //.distinct() // filter out duplicates but it's not needed because we assume that a student can't take the same course twice and the courses are already filtered in the next block
                    .collect(Collectors.toList()); // collects the stream into a list

            studentCourses.addAll(courses); // add the courses of the student to the list

            List<String> distinctCourses = studentCourses.stream() // Stream<String> distinctCourses
                    .distinct() // filter out duplicates
                    .collect(Collectors.toList()); // collects the stream into a list
            //i also tried doing this by overriding the hashCode method for the courses but it was DUMB because the hashCode is the group here
            groupCourseMap.put(group, distinctCourses); // put the updated list of distinct courses back into the map
        }
            //this exercise was HORRIBLE, please don't give us exercises like this in the exam :(
        return groupCourseMap;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<String> strings = Arrays.asList("Ana", "are", "mere", "si", "pere");

        List<Student> students10 = new ArrayList<>();
        students10.add(new Student(3, "Jack", "A"));
        students10.add(new Student(5, "James", "A"));
        students10.add(new Student(11, "Jasper", "A"));
        students10.add(new Student(7, "Joe", "A"));
        students10.add(new Student(9, "Josh", "A"));
        students10.add(new Student(1, "Zohn", "A"));

        students10.add(new Student(8, "Jade", "B"));
        students10.add(new Student(2, "Jane", "B"));
        students10.add(new Student(10, "Jasmine", "B"));
        students10.add(new Student(4, "Jill", "B"));
        students10.add(new Student(12, "Jocelyn", "B"));
        students10.add(new Student(6, "Zenny", "B"));
        
        List<Student> students = Arrays.asList(new Student(1, "John", "A"), new Student(2, "Jane", "B"),
            new Student(3, "Jack", "A"));
        students.get(0).addCourse(new Course(1, 6, 2020, "Java"));
        students.get(0).addCourse(new Course(2, 5, 2020, "Python"));
        students.get(1).addCourse(new Course(1, 5, 2020, "Java"));
        students.get(1).addCourse(new Course(3, 5, 2020, "C++"));
        students.get(2).addCourse(new Course(2, 26, 2020, "Python"));
        students.get(2).addCourse(new Course(3, 5, 2020, "C++"));


        List<Course> courses = Arrays.asList(new Course(1, 5, 2020, "Java"),
            new Course(2, 5, 2020, "Python"), new Course(3, 5, 2020, "C++"));
        courses.get(0).addStudent(new Student(1, "John", "A"));
        courses.get(0).addStudent(new Student(2, "Jane", "B"));
        courses.get(1).addStudent(new Student(1, "John", "A"));
        courses.get(1).addStudent(new Student(3, "Jack", "A"));
        courses.get(2).addStudent(new Student(2, "Jane", "B"));
        courses.get(2).addStudent(new Student(3, "Jack", "A"));

        // Exercise 1
        System.out.println("Exercise 1:");
        List<Integer> result = new App().exercise1(numbers);
        System.out.println(result + "\n");

        // Exercise 2

        System.out.println("Exercise 2:");
        int sum = new App().exercise2(numbers);
        System.out.println(sum + "\n");

        // Exercise 3a
        System.out.println("Exercise 3a:");
        Map<Integer, Long> result3a = new App().exercise3a(strings);
        System.out.println(result3a + "\n");

        // Exercise 3b
        System.out.println("Exercise 3b:");
        Map<Integer, Integer> result3b = new App().exercise3b(strings);
        System.out.println(result3b + "\n");

        // Exercise 4
        System.out.println("Exercise 4:");
        List<List<String>> listOfLists = Arrays.asList(Arrays.asList("Hello", "World"),
            Arrays.asList("Hello", "Lambda"));
        List<String> result4 = new App().exercise4(listOfLists);
        System.out.println(result4 + "\n");

        // Exercise 5
        System.out.println("Exercise 5:");
        String result5 = new App().exercise5(students, "A");
        System.out.println(result5 + "\n");

        // Exercise 6

        // 1.797 seconds for parallel stream
        System.out.println("Exercise 6 parallel stream:");
        long result6P = new App().exercise6P();
        System.out.println(result6P + "\n");

        // 3.777 seconds for sequential stream
        System.out.println("Exercise 6 sequential stream:");
        long result6S = new App().exercise6S();
        System.out.println(result6S + "\n");

        // the parallel stream is twice as fast as the sequential stream

        // Exercise 7
        System.out.println("Exercise 7:");
        List<Student> result7 = new App().exercise7(students);
        System.out.println(result7 + "\n");

        // Exercise 8
        System.out.println("Exercise 8:");
        List<Student> result8 = new App().exercise8(students);
        System.out.println(result8 + "\n");

        // Exercise 9
        System.out.println("Exercise 9:");
        Map<Integer, List<Integer>> result9 = new App().exercise9(courses);
        System.out.println(result9 + "\n");

        // Exercise 10
        System.out.println("Exercise 10:");
        List<Student> result10 = new App().exercise10(students10, "A");
        System.out.println(result10 + "\n");

        // Exercise 11
        System.out.println("Exercise 11:");
        Map<String, List<String>> result11 = new App().exercise11(students);
        System.out.println(result11 + "\n");

    }
}
