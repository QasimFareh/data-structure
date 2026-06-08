import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

    // ===================== قراءة Language Domain File =====================
    public LinkedList<String> readOperands(String filePath) {
        LinkedList<String> operands = new LinkedList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNext()) {
                operands.insert(scanner.next());
            }
        } catch (IOException e) {
            System.out.println("Error reading operands file: " + e.getMessage());
        }

        return operands;
    }

    // ===================== قراءة Operators من Precedence File =====================
    public LinkedList<String> readOperators(String filePath) {
        LinkedList<String> operators = new LinkedList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine(); // تخطي السطر الأول

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                try (Scanner lineScanner = new Scanner(line)) {
                    LinkedList<String> words = new LinkedList<>();
                    while (lineScanner.hasNext())
                        words.insert(lineScanner.next());

                    Node<String> current = words.getHead();
                    while (current != null && current.getNext() != null) {
                        operators.insert(current.getData());
                        current = current.getNext();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading operators file: " + e.getMessage());
        }

        return operators;
    }

    // ===================== قراءة Precedence Rules File =====================
    public LinkedList<String> readPrecedence(String filePath) {
        LinkedList<String> precedence = new LinkedList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            scanner.nextLine(); // تخطي السطر الأول

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                try (Scanner lineScanner = new Scanner(line)) {
                    LinkedList<String> words = new LinkedList<>();
                    while (lineScanner.hasNext())
                        words.insert(lineScanner.next());

                    Node<String> temp = words.getHead();
                    String priority = "";
                    while (temp != null) {
                        if (temp.getNext() == null) priority = temp.getData();
                        temp = temp.getNext();
                    }

                    Node<String> current = words.getHead();
                    while (current != null && current.getNext() != null) {
                        precedence.insert(current.getData() + ":" + priority);
                        current = current.getNext();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading precedence file: " + e.getMessage());
        }

        return precedence;
    }

    // ===================== إرجاع أولوية أوبراتور =====================
    public int getPrecedence(String op, LinkedList<String> precedence) {
        Node<String> current = precedence.getHead();
        while (current != null) {
            String data = current.getData();
            int colon = data.indexOf(':');
            if (data.substring(0, colon).equals(op))
                return Integer.parseInt(data.substring(colon + 1));
            current = current.getNext();
        }
        return -1;
    }

}
