package kr.guardians.falldetection.Server;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

public class AccessToken {
    private String accessToken;
    private Context context;

    public AccessToken(Context context) {
        this.context = context;
    }

    @Nullable
    public String getTokenString() {

        if(accessToken == null){

            SharedPreferences pref = context.getSharedPreferences("guardians",Context.MODE_PRIVATE);
            accessToken = pref.getString("token",null);

        }
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        SharedPreferences pref = context.getSharedPreferences("guardians",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token",accessToken);
        editor.apply();
        this.accessToken = accessToken;
    }

}
