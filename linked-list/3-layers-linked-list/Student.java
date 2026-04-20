package lab_2;


public class Student implements Comparable<Student> {

    String name;
    LinkedList<Course> courseList = new LinkedList<Course>() ;

    public Student(String name) {
        this.name = name;
    }

    public void addCourse(Course c){
        courseList.insert(c);
    }
    public void deleteCourse(Course c){
        courseList.delete(c);
    }
    public boolean findCourse(int courseNum ){
        return courseList.find(new Course(courseNum));
    }
    @Override
    public int compareTo(Student o) {
        return name.compareTo(o.name);
    }

    @Override
    public String toString() {
        return name + "\tCourse List : "+traverse()+"\n";
    }

    public String traverse() {
        return courseList.toString();
    }

}
