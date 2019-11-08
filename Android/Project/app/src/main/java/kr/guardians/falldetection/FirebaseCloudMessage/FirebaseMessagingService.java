package kr.guardians.falldetection.FirebaseCloudMessage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.firebase.messaging.RemoteMessage;
import kr.guardians.falldetection.Activity.SplashActivity;
import kr.guardians.falldetection.R;

import java.util.Map;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public FirebaseMessagingService() {
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("FCM", remoteMessage.toString());
        Log.e("Detail", remoteMessage.getData().toString());

        Map<String, String> datas = remoteMessage.getData();

        Bitmap icon = null;
        if (datas.get("iconImage") == null) {
            icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        } else {
            try {
                icon = Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(datas.get("iconImage"))
                        .submit()
                        .get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Intent resultIntent = new Intent(this, SplashActivity.class);
        resultIntent.putExtra("storeSeq", remoteMessage.getData().get("storeSeq"));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 200, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel("3213", "guardians", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("가디언즈 낙상방지 도우미");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(notificationChannel);
            mBuilder = new NotificationCompat.Builder(getApplicationContext(), notificationChannel.getId())
                    .setContentTitle(datas.get("title"))
                    .setWhen(remoteMessage.getSentTime())
                    .setSmallIcon(R.drawable.icon)
                    .setContentText(datas.get("body"))
                    .setLargeIcon(icon)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);

        }else {

            mBuilder = new NotificationCompat.Builder(getApplicationContext(), "lol")
                    .setContentTitle(datas.get("title"))
                    .setWhen(remoteMessage.getSentTime())
                    .setSmallIcon(R.drawable.icon)
                    .setContentText(datas.get("body"))
                    .setLargeIcon(icon)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
        }

        Notification not = mBuilder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        String k = datas.get("seq");
        if (k != null) {

            int patientSeq = Integer.parseInt(k);
            notificationManager.notify(patientSeq, not);
        } else {
            notificationManager.notify(321, not);
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        Log.e("FCM", "FCM message Received" + s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }


}
