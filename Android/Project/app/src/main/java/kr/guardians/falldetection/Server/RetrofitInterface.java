package kr.guardians.falldetection.Server;

import kr.guardians.falldetection.POJO.Alarm;
import kr.guardians.falldetection.POJO.Patient;
import kr.guardians.falldetection.POJO.Room;
import kr.guardians.falldetection.POJO.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface RetrofitInterface {

    @GET("room")
    Call<Room> getRoomInfo();

    @GET("login")
    Call<User> doLoginProcess(@Query("email")String email, @Query("pw")String pw);

    @FormUrlEncoded
    @POST("register")
    Call<User> doRegisterProcess(@Body User user);

    @GET("tokenLogin")
    Call<User> doLoginWithToken(@Header("Access-Token") String token);

    @GET("searchPatientInfo")
    Call<Patient> getPatientInfo(@Header("Access-Token")String token,@Query("patientSeq")String patientSeq);

    @GET("sendPatientInfo")
    Call<ArrayList<Patient>> getPatientList(@Header("Access-Token")String token, @Query("ordered")String order);

    @GET("autoSearch")
    Call<ArrayList<Patient>> getSearchList(@Query("str")String str);

    @GET("sendAlarmList")
    Call<ArrayList<Alarm>> getAlarmList(@Header("Access-Token")String token);
}
