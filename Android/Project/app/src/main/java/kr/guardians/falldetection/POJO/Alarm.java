package kr.guardians.falldetection.POJO;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import com.google.gson.annotations.SerializedName;
import kr.guardians.falldetection.GlobalApplication;

public class Alarm {
    private String userSeq;
    @SerializedName("timestamp") private long Time;
    private String profileImageUrl;
    private double warningRate;
    private String patientName;
    private String patientSeq;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getUserSeq() {
        return userSeq;
    }

    public void setUserSeq(String userSeq) {
        this.userSeq = userSeq;
    }

    public String getTime() {
        return DateUtils.getRelativeTimeSpanString(Time,System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS).toString();
    }

    public void setTime(long time) {
        Time = time;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public double getWarningRate() {
        return warningRate;
    }

    public void setWarningRate(double warningRate) {
        this.warningRate = warningRate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSeq() {
        return patientSeq;
    }

    public void setPatientSeq(String patientSeq) {
        this.patientSeq = patientSeq;
    }
}
