package model;

public class SubjectDBModel {
    String subjectCode;
    String subjectName;
    double subjectCreditHour;
    double subjectGrade;

    public SubjectDBModel(String subjectCode, String subjectName, double subjectCreditHour, double subjectGrade) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectCreditHour = subjectCreditHour;
        this.subjectGrade = subjectGrade;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public double getSubjectCreditHour() {
        return subjectCreditHour;
    }

    public void setSubjectCreditHour(double subjectCreditHour) {
        this.subjectCreditHour = subjectCreditHour;
    }

    public double getSubjectGrade() {
        return subjectGrade;
    }

    public void setSubjectGrade(double subjectGrade) {
        this.subjectGrade = subjectGrade;
    }

    public String print()
    {
        return "Code: " + subjectCode + ", Name: " + subjectName + ", Credit Hour: " + subjectCreditHour + ", Grade: " + subjectGrade;
    }
}
