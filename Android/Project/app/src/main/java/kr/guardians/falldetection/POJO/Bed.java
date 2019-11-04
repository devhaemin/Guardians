package kr.guardians.falldetection.POJO;

public class Bed {
    private float bedX;
    private float bedY;
    private String patientCode;
    private String patientName;
    private boolean isRed;

    public float getBedX() {
        return bedX;
    }

    public void setBedX(float bedX) {
        this.bedX = bedX;
    }

    public float getBedY() {
        return bedY;
    }

    public void setBedY(float bedY) {
        this.bedY = bedY;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
