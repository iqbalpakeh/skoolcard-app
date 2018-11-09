package com.progrema.skoolcardconsumer.api.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.progrema.skoolcardconsumer.App;
import com.progrema.skoolcardconsumer.R;
import com.progrema.skoolcardconsumer.core.auth.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class FbMessaging extends FirebaseMessagingService {

    private static final String LOG_TAG = FbMessaging.class.getSimpleName();

    private NotificationCompat.Builder mNotificationBuilder;

    private NotificationManager mNotificationManager;

    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();

    /**
     * The registration token may change when:
     * - The app deletes Instance ID
     * - The app is restored on a new device
     * - The user uninstalls/reinstall the app
     * - The user clears app data.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(LOG_TAG, "Refreshed token: " + token);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            sendRegistrationToServer(token);
        }
    }

    private void sendRegistrationToServer(String token) {
        mDatabase.collection(FbBase.ROOT_CONSUMERS)
                .document(App.getUID(getApplicationContext()))
                .update("token", token)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(LOG_TAG, "New token updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(LOG_TAG, "Error updating token", e);
                    }
                });
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(LOG_TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(LOG_TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData());
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageData FCM message body received.
     */
    private void sendNotification(Map<String, String> messageData) {

        JSONObject data = new JSONObject(messageData);

        String messageTitle = "";
        String messageBody = "";

        try {
            messageTitle = data.getString("title");
            messageBody = data.getString("body");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "data = " + data.toString());
        Log.d(LOG_TAG, "title = " + messageTitle);
        Log.d(LOG_TAG, "body = " + messageBody);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (mNotificationBuilder == null) {
            Log.d(LOG_TAG, "new builder");
            mNotificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);
        }

        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mNotificationBuilder.build());
        }

    }
}
