package kr.guardians.falldetection.POJO;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import kr.guardians.falldetection.GlobalApplication;

public class Alarm {
    private String name;
    private long timeSec;
    private float progress;
    private boolean isChecked;
    private String profileImageUrl;
    private String patientCode;


    public Alarm(String name, long timeSec, float progress, boolean isChecked, String profileImageUrl) {
        this.name = name;
        this.timeSec = timeSec;
        this.progress = progress;
        this.isChecked = isChecked;
        this.profileImageUrl = profileImageUrl;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeSec() {
        return DateUtils.getRelativeTimeSpanString(timeSec,System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS).toString();
    }

    public void setTimeSec(long timeSec) {
        this.timeSec = timeSec;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
