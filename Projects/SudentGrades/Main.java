import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GradeBST gradeBST = new GradeBST();
        Scanner sc = new Scanner(System.in);

        System.out.print("عدد الطلاب: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.print("الاسم: ");
            String name = sc.nextLine();
            System.out.print("العلامة: ");
            int grade = sc.nextInt();
            sc.nextLine();
            gradeBST.addStudent(new Student(name, grade));
        }

        System.out.println("\nالنتيجة:");
        gradeBST.printAll();
    }
}
