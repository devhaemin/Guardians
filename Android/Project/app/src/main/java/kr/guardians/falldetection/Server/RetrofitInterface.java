package kr.guardians.falldetection.Server;

import kr.guardians.falldetection.POJO.Room;
import kr.guardians.falldetection.POJO.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface RetrofitInterface {

    @GET("room")
    Call<Room> getRoomInfo();

    @GET("login")
    Call<User> doLoginProcess(@Body User user);

    @FormUrlEncoded
    @POST("register")
    Call<User> doRegisterProcess(@Body User user);

    @GET("tokenLogin")
    Call<User> doLoginWithToken(@Header("Access-Token") String token);


}
