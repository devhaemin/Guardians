package kr.guardians.falldetection;


import android.app.Application;
import android.content.Context;
import kr.guardians.falldetection.POJO.User;
import kr.guardians.falldetection.Server.AccessToken;

public class GlobalApplication extends Application {
    private AccessToken accessToken;
    private User user;


    @Override
    public void onCreate() {
        super.onCreate();
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccessToken getAccessToken() {
        if(accessToken == null){
            accessToken = new AccessToken(this);
        }
        return accessToken;
    }

    public void setAccessToken(String token) {
        accessToken.setAccessToken(token);
    }

}
