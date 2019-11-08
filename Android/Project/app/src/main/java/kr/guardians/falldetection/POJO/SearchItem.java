package kr.guardians.falldetection.POJO;

public class SearchItem {
    private String text;
    private long timeSec;
    private String patientCode;

    public SearchItem(String text, long timeSec, String patientCode) {
        this.text = text;
        this.timeSec = timeSec;
        this.patientCode = patientCode;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(long timeSec) {
        this.timeSec = timeSec;
    }
}
