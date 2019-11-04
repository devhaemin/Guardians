package kr.guardians.falldetection.Server;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class ResponseCheckProcess<T> {

    Context context;
    String TAG;

    public ResponseCheckProcess(Context context, String TAG) {
        this.context = context;
        this.TAG = TAG;
    }

    public void onFailure(Call<T> res, Throwable t){
        Log.e(TAG, t.getMessage() + "");
        Toast.makeText(context, "서버 접속이 원활하지 않습니다.\n네트워크 연결 상태를 확인해주세요.", Toast.LENGTH_SHORT).show();
    }
    public boolean isVaild(Call<T> resCall, Response<T> response){
        if(response.code() == 200 && response.body() != null){
            Log.e(TAG,"Success Message : "+response.code()+" / "+response.message());
            return true;
        }else{
            ResponseBody responseBody = response.errorBody();
            if(responseBody != null) Log.e(TAG, "The Server Response is : "+ new Gson().toJson(responseBody));
           // Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
