public class Major implements  Comparable<Major>{

    private String majorName;
    private int capacity;
    private double  acceptanceGrade;
    private double  tawjihiWeight;
    private double  placementWeight;
    private DoubleLinkedList<Student> acceptedList;
    private DoubleLinkedList<Student> rejectedList;

    public Major(String majorName, int capacity, double acceptanceGrade, double tawjihiWeight, double placementWeight) {
        this.majorName = majorName;
        this.capacity = capacity;
        this.acceptanceGrade = acceptanceGrade;
        this.tawjihiWeight = tawjihiWeight;
        this.placementWeight = placementWeight;
        this.acceptedList = new DoubleLinkedList<>(false);
        this.rejectedList = new DoubleLinkedList<>(true);
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getAcceptanceGrade() {
        return acceptanceGrade;
    }

    public void setAcceptanceGrade(double acceptanceGrade) {
        this.acceptanceGrade = acceptanceGrade;
    }

    public double getTawjihiWeight() {
        return tawjihiWeight;
    }

    public void setTawjihiWeight(double tawjihiWeight) {
        this.tawjihiWeight = tawjihiWeight;
    }

    public double getPlacementWeight() {
        return placementWeight;
    }

    public void setPlacementWeight(double placementWeight) {
        this.placementWeight = placementWeight;
    }

    public DoubleLinkedList<Student> getAcceptedList() {
        return acceptedList;
    }

    public void setAcceptedList(DoubleLinkedList<Student> acceptedList) {
        this.acceptedList = acceptedList;
    }

    public DoubleLinkedList<Student> getRejectedList() {
        return rejectedList;
    }

    public void setRejectedList(DoubleLinkedList<Student> rejectedList) {
        this.rejectedList = rejectedList;
    }

    @Override
    public String toString() {
        return majorName + ", " + capacity + ", " + acceptanceGrade + ", " + tawjihiWeight + ", " + placementWeight;
    }

    @Override
    public int compareTo(Major o) {
        return this.majorName.compareTo(o.majorName);
    }
}
