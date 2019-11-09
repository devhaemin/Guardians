package kr.guardians.falldetection.POJO;

import android.content.Context;
import android.text.format.DateUtils;
import com.google.gson.annotations.SerializedName;

public class Alarm {
    private String userSeq;
    @SerializedName("timeStamp") private long timeStamp;
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

    public String getTimeStamp(Context context) {
        return DateUtils.getRelativeTimeSpanString(timeStamp,System.currentTimeMillis(),10000).toString();
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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
