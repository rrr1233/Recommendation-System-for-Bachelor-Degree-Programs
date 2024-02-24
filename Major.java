
public class Major {

    private String name;
    private double MinIndustrySalary;
    private float MinpreGPA;
    private String instrestsLevel;
    private String JobCategory;
    private float acceptableGPA;
    private int point = 0;

    public Major(String name, int minIndustrySalary, float minpreGPA, String interestLevel, String jobCategory, float acceptableGPA) {
        this.name = name;
        this.MinIndustrySalary = minIndustrySalary;
        this.MinpreGPA = minpreGPA;
        this.instrestsLevel = interestLevel;
        this.JobCategory = jobCategory;
        this.acceptableGPA = acceptableGPA;
    }

    public void givePoint() {
        point++;
    }

    public double getMinIndustrySalary() {
        return MinIndustrySalary;
    }

    public double getPreviousGPA() {
        return MinpreGPA;
    }

    public String getProgrammingInterest() {
        return instrestsLevel;
    }

    int getPoint() {
        return point;
    }

    String getJobCategory() {
        return JobCategory;
    }

    float getAcceptableGPA() {
        return acceptableGPA;
    }

    String getName() {
        return name;
    }

}
