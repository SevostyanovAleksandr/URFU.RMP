package com.android.example.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;


/*
 Служба, которую JobScheduler запускает после выполнения условий.
 В этом случае он отправляет уведомление.
 */
public class NotificationJobService extends JobService {

    // Идентификатор канала уведомления.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    // Менеджер уведомлений.
    NotificationManager mNotifyManager;

    /**
     Вызывается системой, как только она определяет, что пришло время выполнить задание.
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters) {

        // Создайте канал уведомлений.
        createNotificationChannel();

        // Настройте намерение содержимого уведомления для запуска приложения, когда
        // нажал.
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (this, 0, new Intent(this, MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (this, PRIMARY_CHANNEL_ID)
                .setContentTitle(getString(R.string.job_service))
                .setContentText(getString(R.string.job_running))
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(0, builder.build());
        return false;
    }

    /**
     Вызывается системой, когда задание выполняется, но условия
     больше не выполняются.
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    /**
     * Создает канал уведомлений для OREO и выше.
     */
    public void createNotificationChannel() {

// Создайте объект диспетчера уведомлений.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

// Каналы уведомлений доступны только в OREO и выше.
        // Итак, добавьте проверку версии SDK.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // // Создайте канал уведомлений со всеми параметрами.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            getString(R.string.job_service_notification),
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    (getString(R.string.notification_channel_description));

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}
