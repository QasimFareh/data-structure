package lab_2;

public class Course implements Comparable<Course> {

    int num;
    LinkedList<Quiz> quizList = new LinkedList<Quiz>() ;


    public Course(int num) {
        this.num = num;
    }

    public void addQuiz(Quiz quiz){
        quizList.insert(quiz);
    }
    public void deleteQuiz(Quiz quiz){
        quizList.delete(quiz);
    }
    public boolean findQuiz(int quizNum ){
        return quizList.find(new Quiz(quizNum));
    }

    @Override
    public String toString() {
        return num + "\tQuiz List : "+traverse()+"\n";
    }

    @Override
    public int compareTo(Course o) {
        return num - o.num;
    }


    public String traverse() {
        return quizList.toString();
    }

}
