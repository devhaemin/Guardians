package kr.guardians.falldetection.FirebaseCloudMessage;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import kr.guardians.falldetection.GlobalApplication;
import kr.guardians.falldetection.POJO.User;
import kr.guardians.falldetection.Server.RetrofitClient;
import kr.guardians.falldetection.Server.RetrofitInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class MyService extends FirebaseInstanceIdService {

    public MyService() {
    }


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        // If you want to send messages to this application instance or

        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String token) {
        // TODO: Implement this method to send token to your app server.
        Log.e("tokenRefresh", "Firebase Token Refreshed");



    }
}
