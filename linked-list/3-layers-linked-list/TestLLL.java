package lab_2;

public class TestLLL {

    public static void main(String[] args) {

        LinkedList<Student> soso = new LinkedList<Student>();
        Student qasim = new Student("Qsaim");
        Course c1 = new Course(136);
        qasim.addCourse(c1);
        qasim.addCourse(new Course(242));
        c1.addQuiz(new Quiz(1));
        c1.addQuiz(new Quiz(2));
        soso.insert(qasim);

        Student ahmed = new Student("Ahmed");
        Course c2 = new Course(242);
        ahmed.addCourse(new Course(136));
        ahmed.addCourse(c2);
        c2.addQuiz(new Quiz(1));
        c2.addQuiz(new Quiz(2));
        soso.insert(ahmed);

        qasim.deleteCourse(c1);

        soso.traverse();

        LinkedList<Integer> l1 = new LinkedList<>();
        l1.insert(1);
        l1.insert(3);
        l1.insert(5);
        l1.insert(6);
        LinkedList<Integer> l2 = new LinkedList<>();
        l2.insert(2);
        l2.insert(3);
        l2.insert(4);
        l2.insert(7);
        LinkedList<Integer> res = new LinkedList<>();
        res = res.marrTLRec(l1,l2);
        res.traverse();



    }

}


