package kr.guardians.falldetection.POJO;

public class Patient {


    String name;
    String phone;

    String roomCode;
    String hospitalCode;
    String imageUrl;

    int gender;
    int age;
    double warningRate;
    int patientCode;



    public Patient(String name, String phone, String roomCode, String hospitalCode, String imageUrl, int gender, int age, double warningRate,int patientCode) {
        this.name = name;
        this.phone = phone;
        this.roomCode = roomCode;
        this.hospitalCode = hospitalCode;
        this.imageUrl = imageUrl;
        this.gender = gender;
        this.age = age;
        this.warningRate = warningRate;
    }

    public int getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(int patientCode) {
        this.patientCode = patientCode;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
