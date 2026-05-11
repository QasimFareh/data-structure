import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AdmissionSystem {
    private DoubleLinkedList<Major> majors;
    private DoubleLinkedList<Student> allStudents = new DoubleLinkedList<>(true);


    public AdmissionSystem() {
        majors = new DoubleLinkedList<>(true);
    }

    // ============================================================================================================================================
    // ادخال الملفات الثنين
    // ============================================================================================================================================

    public void loadCriteria(String f) {
        try (Scanner sc = new Scanner(new File(f))) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                String majorName = parts[0].trim();
                int capacity = Integer.parseInt(parts[1].trim());
                double acceptanceGrade = Double.parseDouble(parts[2].trim());
                double tawjihiWeight = Double.parseDouble(parts[3].trim());
                double placementWeight = Double.parseDouble(parts[4].trim());
                Major m = new Major(majorName, capacity, acceptanceGrade, tawjihiWeight, placementWeight);
                majors.insert(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadStudents(String f) {
        try (Scanner sc = new Scanner(new File(f))) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty()) continue;
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String fullName = parts[1].trim();
                String[] nameParts = fullName.split(" ");
                String firstName, familyName;
                if (nameParts.length >= 2) {
                    firstName = nameParts[0];
                    familyName = nameParts[1];
                } else {
                    firstName = fullName;
                    familyName = "";
                }
                double tawjihiGrade = Double.parseDouble(parts[2]);
                double placementGrade = Double.parseDouble(parts[3]);
                String chosenMajor = parts[4].trim();
                Major major = majors.find(new Major(chosenMajor, 0, 0, 0, 0));

                if (major != null) {
                    double admissionMark = tawjihiGrade * major.getTawjihiWeight() + placementGrade * major.getPlacementWeight();
                    Student s = new Student(id, firstName, familyName, tawjihiGrade, placementGrade, chosenMajor, admissionMark);
                    if (admissionMark >= major.getAcceptanceGrade() && major.getCapacity() != 0) {
                        major.getAcceptedList().insert(s);
                        major.setCapacity(major.getCapacity() - 1);
                        s.setAccepted(true);
                    } else {
                        boolean lowMark = admissionMark < major.getAcceptanceGrade();
                        boolean fullCapacity = major.getCapacity() == 0;

                        if (lowMark && fullCapacity) {
                            s.setRejectionReason("Low Mark & Capacity Full");
                        } else if (fullCapacity) {
                            s.setRejectionReason("Capacity Full");
                        } else {
                            s.setRejectionReason("Low Admission Mark");
                        }
                        major.getRejectedList().insert(s);
                        s.setAccepted(false);
                    }

                    allStudents.insert(s);
                }
            }
        } catch (Exception e) {

        }

    }

    // ============================================================================================================================================
    // STUDENT
    // ============================================================================================================================================


    public Student findSt(int id) {
        Node<Student> curr = allStudents.getHead().getNext();
        while (curr != allStudents.getHead()) {
            if (curr.getData().getId() == id)
                return curr.getData();
            curr = curr.getNext();
        }
        return null;
    }

    public Student deleteSt(int id) {
        String majorName;
        Node<Student> curr = allStudents.getHead().getNext();
        while (curr != allStudents.getHead()) {
            if (curr.getData().getId() == id) {
                majorName = curr.getData().getChosenMajor();
                Major m = majors.find(new Major(majorName, 0, 0, 0, 0));
                if (m == null) return null;
                if (curr.getData().isAccepted()) {
                    m.getAcceptedList().delete(curr.getData());
                } else {
                    m.getRejectedList().delete(curr.getData());
                }
                allStudents.delete(curr.getData());
                return curr.getData();
            }
            curr = curr.getNext();
        }
        return null;
    }

    public void updateStudent(int id, String firstName, String lastName, double tawjihiGrade, double placementGrade) {
        Student s = findSt(id);
        if (s == null) return;

        Major m = majors.find(new Major(s.getChosenMajor(), 0, 0, 0, 0));
        if (m == null) return;

        // طيرتو من اللست القديمة
        if (s.isAccepted()) {
            m.getAcceptedList().delete(s);
        } else {
            m.getRejectedList().delete(s);
        }

        // تعديل الحقول
        s.setFirstName(firstName);
        s.setFamilyName(lastName);
        s.setTawjihiGrade(tawjihiGrade);
        s.setPlacementGrade(placementGrade);
        s.setAdmissionMark(tawjihiGrade * m.getTawjihiWeight() + placementGrade * m.getPlacementWeight());

        // ضفتو بمكانو الصح هس
        if (s.getAdmissionMark() >= m.getAcceptanceGrade() && m.getCapacity() != 0) {
            m.getAcceptedList().insert(s);
            s.setAccepted(true);
        } else {
            m.getRejectedList().insert(s);
            s.setAccepted(false);
        }
    }

    public void insertStudent(int id, String firstName, String familyName, double tawjihiGrade, double placementGrade, String chosenMajor) {
        Major major = majors.find(new Major(chosenMajor, 0, 0, 0, 0));
        if (major == null) return;
        double admissionMark = tawjihiGrade * major.getTawjihiWeight() + placementGrade * major.getPlacementWeight();
        Student s = new Student(id, firstName, familyName, tawjihiGrade, placementGrade, chosenMajor, admissionMark);
        if (admissionMark >= major.getAcceptanceGrade() && major.getCapacity() != 0) {
            major.getAcceptedList().insert(s);
            major.setCapacity(major.getCapacity() - 1);
            s.setAccepted(true);
        } else {
            boolean lowMark = admissionMark < major.getAcceptanceGrade();
            boolean fullCapacity = major.getCapacity() == 0;

            if (lowMark && fullCapacity) {
                s.setRejectionReason("Low Mark & Capacity Full");
            } else if (fullCapacity) {
                s.setRejectionReason("Capacity Full");
            } else {
                s.setRejectionReason("Low Admission Mark");
            }
            major.getRejectedList().insert(s);
            s.setAccepted(false);
        }

        allStudents.insert(s);
    }

    public Node<Student> getAllStudentsHead() {
        return allStudents.getHead();
    }
    // ============================================================================================================================================
    // MAJOR
    // ============================================================================================================================================

    public void insertMajor(String majorName, int capacity, double acceptanceGrade, double tawjihiWeight, double placementWeight) {
        Major m = new Major(majorName, capacity, acceptanceGrade, tawjihiWeight, placementWeight);
        majors.insert(m);
    }

    public Major deleteMajor(String majorName) {
        Major m = majors.find(new Major(majorName, 0, 0, 0, 0));
        if (m == null) return null;
        else {
            majors.delete(m);
            return m;
        }
    }

    public void updateMajor(String oldMajorName, String newMajorName, int capacity, double acceptanceGrade, double tawjihiWeight, double placementWeight) {
        Major m = majors.find(new Major(oldMajorName, 0, 0, 0, 0));
        if (m == null) return;
        majors.delete(m);// منحذفها لانو ممكن بعد ما نغير الاسم يتغير الترتيب
        m.setMajorName(newMajorName);
        m.setCapacity(capacity);
        m.setAcceptanceGrade(acceptanceGrade);
        m.setTawjihiWeight(tawjihiWeight);
        m.setPlacementWeight(placementWeight);
        majors.insert(m); // واكيد ما مننسى نرجع نضيفها
    }

    public Major searchMajor(String majorName) {
        Node<Major> curr = majors.getHead().getNext();
        while (curr != majors.getHead()) {
            if (curr.getData().getMajorName().equalsIgnoreCase(majorName)) {
                return curr.getData();
            }
            curr = curr.getNext();
        }
        return null;
    }

    public Node<Major> getMajorsHead() {
        return majors.getHead();
    }

    public void clearMajors() {
        majors = new DoubleLinkedList<>(true);
    }


    // ============================================================================================================================================
    // طباعات
    // ============================================================================================================================================

    // بطبعلي الطلاب المقيولين والمرفوضين حسب الطلب باي تخصص انا بدي اياه
    public void printStudentList(String majorName, boolean accepted) {
        Major m = majors.find(new Major(majorName, 0, 0, 0, 0));
        if (m == null) return;
        else {
            if (accepted) {
                m.getAcceptedList().traverse();
            } else {
                m.getRejectedList().traverse();
            }
        }
    }

    // بطبعلي التخصصات المقترحة لكل طالب
    public DoubleLinkedList<Major> SelectionAndRecommendation(double tawjihiGrade, double placementGrade, String chosenMajor) {
        DoubleLinkedList<Major> recommended = new DoubleLinkedList<>(true);

        // اذا ما دخلي اسم مادة

        if (chosenMajor == null) {
            Node<Major> curr = majors.getHead().getNext();
            while (curr != majors.getHead()) {
                double tW = curr.getData().getTawjihiWeight();
                double pW = curr.getData().getPlacementWeight();
                double admissionMarkForSt = ((tawjihiGrade * tW) + (placementGrade * pW));
                if (admissionMarkForSt >= curr.getData().getAcceptanceGrade() && curr.getData().getCapacity() != 0) {
                    recommended.insert(curr.getData());
                }
                curr = curr.getNext();
            }
            // اذا دخل اسم مادة
        } else {
            Major m = majors.find(new Major(chosenMajor, 0, 0, 0, 0));
            if (m == null) return recommended;
            double tW = m.getTawjihiWeight();
            double pW = m.getPlacementWeight();
            double admissionMarkForSt = ((tawjihiGrade * tW) + (placementGrade * pW));
            // اذا مقبول فيها
            if (admissionMarkForSt >= m.getAcceptanceGrade() && m.getCapacity() != 0) {
                recommended.insert(m);
            } else { // اذا مش مقبول فيها
                Node<Major> curr = majors.getHead().getNext();
                // لازم ارجع احسب كل معدل قبول لانو كل مادة الها وزنها
                while (curr != majors.getHead()) {
                    double tW2 = curr.getData().getTawjihiWeight();
                    double pW2 = curr.getData().getPlacementWeight();
                    double admissionMarkForSt2 = (tawjihiGrade * tW2) + (placementGrade * pW2);
                    if (admissionMarkForSt2 >= curr.getData().getAcceptanceGrade() && curr.getData().getCapacity() != 0) {
                        recommended.insert(curr.getData());
                    }
                    curr = curr.getNext();
                }
            }
        }

        return recommended;

    }

    // ============================================================================================================================================
    // الاحصائيات
    // ============================================================================================================================================


    //بدي اعطيها اسم التخصص وتطبعلي المقبولين فيه -العدد-
    public int totalAcceptedInChosenMajor(String chosenMajor) {
        Major m = majors.find(new Major(chosenMajor, 0, 0, 0, 0));
        if (m == null) return 0;
        return m.getAcceptedList().size();
    }

    //بدي اعطيها اسم التخصص وتطبعلي المرفوضين فيه -العدد-
    public int totalRejectedInChosenMajor(String chosenMajor) {
        Major m = majors.find(new Major(chosenMajor, 0, 0, 0, 0));
        if (m == null) return 0;
        return m.getRejectedList().size();
    }

    //طباعة كل المقبولين بكل التخصصات -العدد-
    public int totalAccepted() {
        int size = 0;
        Node<Major> curr = majors.getHead().getNext();
        return totalAccepted(size, curr);
    }

    private int totalAccepted(int size, Node<Major> curr) {
        if (curr != majors.getHead()) {
            size += curr.getData().getAcceptedList().size();
            return totalAccepted(size, curr.getNext());
        }
        return size;
    }

    //طباعة كل المرفوضين بكل التخصصات -العدد-
    public int totalRejected() {
        int size = 0;
        Node<Major> curr = majors.getHead().getNext();
        return totalRejected(size, curr);
    }

    private int totalRejected(int size, Node<Major> curr) {
        if (curr != majors.getHead()) {
            size += curr.getData().getRejectedList().size();
            return totalRejected(size, curr.getNext());
        }
        return size;
    }

    // بتطبعلي النسبة للمقبولين على اللي مقدمين
    public double acceptanceRate() {
        int accepted = totalAccepted();
        int rejected = totalRejected();
        int total = accepted + rejected;
        if (total == 0) return 0;
        return ((double) accepted / total) * 100;
    }

    // طباعة N من التخصص الفلاني
    public DoubleLinkedList<Student> topNStudents(String chosenMajor, int N) {
        DoubleLinkedList<Student> nOfSt = new DoubleLinkedList<>(false);
        Major m = majors.find(new Major(chosenMajor, 0, 0, 0, 0));
        if (m == null) return null;
        Node<Student> curr = m.getAcceptedList().getHead().getNext();
        for (int i = 0; i < N; i++) {
            if (curr == m.getAcceptedList().getHead()) break;
            nOfSt.insert(curr.getData());
            curr = curr.getNext();
        }

        return nOfSt;
    }


    // ============================================================================================================================================
    // حفط الملفين majors & students
    // ============================================================================================================================================

    //طباعة ال majors
    public void saveCriteria(String f) {
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("Major, Capacity, Acceptance grade, Tawjihi Weight, Placement Test Weight");
            Node<Major> curr = majors.getHead().getNext();
            while (curr != majors.getHead()) {
                pw.println(curr.getData().toString());
                curr = curr.getNext();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //طباعة ال students
    public void saveStudents(String f) {
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.println("Student ID, First and Family Name, Tawjihi Grade, Placement Test Grade, Chosen Major");
            Node<Student> curr = allStudents.getHead().getNext();
            while (curr != allStudents.getHead()) {
                pw.println(curr.getData().toString());
                curr = curr.getNext();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}


// ============================================================================================================================================
// D O N E ! ! ! ! !
// ============================================================================================================================================


