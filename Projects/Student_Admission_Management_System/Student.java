public class Student implements Comparable<Student> {
    private int id;
    private String firstName;
    private String familyName;
    private double tawjihiGrade;
    private double placementGrade;
    private String chosenMajor;
    private double admissionMark;
    private boolean isAccepted;
    private String rejectionReason;


    public Student(int id, String firstName, String familyName, double tawjihiGrade, double placementGrade, String chosenMajor, double admissionMark) {
        this.id = id;
        this.firstName = firstName;
        this.familyName = familyName;
        this.tawjihiGrade = tawjihiGrade;
        this.placementGrade = placementGrade;
        this.chosenMajor = chosenMajor;
        this.admissionMark = admissionMark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public double getTawjihiGrade() {
        return tawjihiGrade;
    }

    public void setTawjihiGrade(double tawjihiGrade) {
        this.tawjihiGrade = tawjihiGrade;
    }

    public double getPlacementGrade() {
        return placementGrade;
    }

    public void setPlacementGrade(double placementGrade) {
        this.placementGrade = placementGrade;
    }

    public double getAdmissionMark() {
        return admissionMark;
    }

    public void setAdmissionMark(double admissionMark) {
        this.admissionMark = admissionMark;
    }

    public String getChosenMajor() {
        return chosenMajor;
    }

    public void setChosenMajor(String chosenMajor) {
        this.chosenMajor = chosenMajor;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getFullName() {return firstName + " " + familyName; }

    public String getRejectionReason() { return rejectionReason; }

    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }


    @Override
    public String toString() {
        return id + ", " + firstName + " " + familyName + ", " + tawjihiGrade + ", " + placementGrade + ", " + chosenMajor;
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(this.admissionMark, o.admissionMark);
    }
}
