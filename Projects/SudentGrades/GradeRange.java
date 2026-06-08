public class GradeRange implements Comparable<GradeRange> {
    String range;
    int low;
    LinkedList<Student> students;

    public GradeRange(int low, int high) {
        this.low = low;
        this.range = low + "-" + high;
        this.students = new LinkedList<>();
    }

    public void addStudent(Student s) {
        students.insert(s);
    }

    @Override
    public int compareTo(GradeRange other) {
        return Integer.compare(this.low, other.low);
    }

    @Override
    public String toString() {
        return range + ": ";
    }
}