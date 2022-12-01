package com.android.example.notifyme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 Основное действие для приложения Notify Me!. Содержит три кнопки, которые доставляют,
 обновляют и отменяют уведомление.
 */
public class MainActivity extends AppCompatActivity {

    // Константы для кнопок действий с уведомлениями.
    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.android.example.notifyme.ACTION_UPDATE_NOTIFICATION";
    // Идентификатор канала уведомления.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    // Идентификатор уведомления.
    private static final int NOTIFICATION_ID = 0;

    private Button button_notify;
    private Button button_cancel;
    private Button button_update;

    private NotificationManager mNotifyManager;

    private NotificationReceiver mReceiver = new NotificationReceiver();

    /**
     Инициализирует действие.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Создайте канал уведомлений.
        createNotificationChannel();

// Зарегистрируйте широковещательный приемник для получения действия обновления от
        // уведомление.
        registerReceiver(mReceiver,
                new IntentFilter(ACTION_UPDATE_NOTIFICATION));

        // Добавьте обработчики onClick ко всем кнопкам.
        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Send the notification
                sendNotification();
            }
        });

        button_update = (Button) findViewById(R.id.update);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Обновите уведомление.
                updateNotification();
            }
        });

        button_cancel = (Button) findViewById(R.id.cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Отменить уведомление.
                cancelNotification();
            }
        });

// Сбросьте состояние кнопки. Включить только кнопку уведомления и отключить
        // кнопки обновления и отмены.
        setNotificationButtonState(true, false, false);
    }

    /**
     Отмените регистрацию получателя, когда приложение будет уничтожено.
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    /**
     Создает канал уведомлений для OREO и выше.
     */
    public void createNotificationChannel() {

        // Создайте объект диспетчера уведомлений.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

// Каналы уведомлений доступны только в OREO и выше.
        // Итак, добавьте проверку версии SDK.
        if (android.os.Build.VERSION.SDK_INT >=
                                    android.os.Build.VERSION_CODES.O) {

// Создайте канал уведомлений со всеми параметрами.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            getString(R.string.notification_channel_name),
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    (getString(R.string.notification_channel_description));

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     Метод OnClick для кнопки "Уведомить меня!".
     Создает и доставляет простое уведомление.
     */
    public void sendNotification() {

// Устанавливает ожидающее намерение обновить уведомление.
        // Соответствует нажатию кнопки Обновить меня!.
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

// Создайте уведомление со всеми параметрами с помощью помощника
        // способ.
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

// Добавьте кнопку действия, используя ожидающее намерение.
        notifyBuilder.addAction(R.drawable.ic_update,
                            getString(R.string.update), updatePendingIntent);

// Доставить уведомление.
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

// Включает кнопки обновления и отмены, но отключает "Уведомлять
        // Я!" кнопка.
        setNotificationButtonState(false, true, true);
    }

    /**
     Вспомогательный метод, который создает уведомление.
     */
    private NotificationCompat.Builder getNotificationBuilder() {

// Настройте ожидающее намерение, которое доставляется при получении уведомления
        // нажата.
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

// Создайте уведомление со всеми параметрами.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_android)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    /**
     * Метод OnClick для кнопки "Обновить меня!". Обновляет существующий
     * уведомление о показе картинки.
     */
    public void updateNotification() {

// Загрузите доступный для рисования ресурс в растровое изображение.
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(), R.drawable.mascot_1);

// Создайте уведомление со всеми параметрами с помощью помощника
// способ.
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

// Обновите стиль уведомления до BigPictureStyle.
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle(getString(R.string.notification_updated)));

// Доставить уведомление.
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        // Отключите кнопку обновления, оставив включенной только кнопку отмены.
        setNotificationButtonState(false, false, true);
    }

    /**
     * Метод OnClick для кнопки "Отменить меня!". Отменяет уведомление.
     */
    public void cancelNotification() {
// Отменить уведомление.
        mNotifyManager.cancel(NOTIFICATION_ID);

// Сбросьте кнопки.
        setNotificationButtonState(true, false, false);
    }

    /**
     Вспомогательный метод для включения / выключения кнопок.
     */
    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean
                                    isUpdateEnabled, Boolean isCancelEnabled) {
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }

    /**
     * Класс широковещательного приемника для уведомлений.
     * Отвечает на уведомление об обновлении в ожидании действия намерения.
     */
    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        /**
         Принимает входящие радиопередачи и отвечает соответствующим образом.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Обновите уведомление.
            updateNotification();
        }
    }
}
