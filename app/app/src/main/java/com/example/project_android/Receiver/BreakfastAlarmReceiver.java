package com.example.project_android.Receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.project_android.R;

public class BreakfastAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("meal_channel", "Meal Time", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        String title = "Bữa ăn";
        String message = "Đã đến giờ ăn!";

        long alarmTime = intent.getLongExtra("alarmTime", 0);
        int currentHour = (int) (alarmTime / (1000 * 60 * 60) % 24);

        if (currentHour >= 5 && currentHour < 9) {
            title = "Đã đến giờ ăn sáng!";
            message = "Thời gian để bắt đầu ngày mới với bữa sáng ngon lành.";
        } else if (currentHour >= 9 && currentHour < 13) {
            title = "Đã đến giờ ăn trưa!";
            message = "Đã đến lúc thưởng thức bữa trưa ngon miệng.";
        } else if (currentHour >= 13 && currentHour < 20) {
            title = "Đã đến giờ ăn chiều!";
            message = "Đừng quên ăn một bữa chiều bổ dưỡng!";
        } else if (currentHour >= 20 && currentHour < 24) {
            title = "Đã đến giờ ăn tối!";
            message = "Chúc bạn thưởng thức bữa tối ngon miệng!";
        }

        Notification notification = new NotificationCompat.Builder(context, "meal_channel")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(1, notification);
    }

}
