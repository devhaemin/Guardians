package kr.guardians.falldetection.POJO;

import android.graphics.Rect;

public class Bed {
    private float bedX;
    private float bedY;
    private String patientSeq;
    private String patientName;
    private boolean isRed;
    private Rect rect;

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public float getBedX() {
        return bedX;
    }

    public boolean isRed(String patientCode) {
        return patientCode.equals(this.patientSeq);
    }

    public float getBedY() {
        return bedY;
    }

    public void setBedX(float bedX) {
        this.bedX = bedX;
    }

    public void setBedY(float bedY) {
        this.bedY = bedY;
    }

    public String getPatientSeq() {
        return patientSeq;
    }

    public void setPatientSeq(String patientSeq) {
        this.patientSeq = patientSeq;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
