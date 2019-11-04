package kr.guardians.falldetection.POJO;

public class Patient {


    private String patientName;
    private String phone;

    private String roomCode;
    private String hospitalCode;
    private String imageUrl;
    private String profileImageUrl;

    private int gender;
    private int age;
    private double warningRate;
    private String patientSeq;
    private String painInfo;

    private int bodyStatus;



    public Patient(String patientName, String phone, String roomCode, String hospitalCode, String imageUrl, int gender, int age, double warningRate, String patientSeq) {
        this.patientName = patientName;
        this.phone = phone;
        this.roomCode = roomCode;
        this.hospitalCode = hospitalCode;
        this.imageUrl = imageUrl;
        this.gender = gender;
        this.age = age;
        this.warningRate = warningRate;
    }

    public int getBodyStatus() {
        return bodyStatus;
    }

    public void setBodyStatus(int bodyStatus) {
        this.bodyStatus = bodyStatus;
    }

    public String getPainInfo() {
        return painInfo;
    }

    public void setPainInfo(String painInfo) {
        this.painInfo = painInfo;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPatientSeq() {
        return patientSeq;
    }

    public void setPatientSeq(String patientSeq) {
        this.patientSeq = patientSeq;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
}
