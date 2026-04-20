package lab_2;

public class Quiz implements Comparable<Quiz>{
    int quizNum ;

    public Quiz(int quizNum) {
        this.quizNum = quizNum;
    }

    @Override
    public int compareTo(Quiz o) {
        return quizNum - o.quizNum;
    }

    @Override
    public String toString() {
        return quizNum + "";
    }
}
