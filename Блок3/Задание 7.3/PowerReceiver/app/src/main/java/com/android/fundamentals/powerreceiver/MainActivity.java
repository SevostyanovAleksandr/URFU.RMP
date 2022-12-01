package com.android.fundamentals.powerreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 Приложение Power Receiver реагирует на системные трансляции о питании
 состояние подключения, а также пользовательская широковещательная передача, которая отправляется, когда пользователь
 нажимает на кнопку.
 */
public class MainActivity extends AppCompatActivity {

    private CustomReceiver mReceiver = new CustomReceiver();

    // Строковая константа, определяющая пользовательское широковещательное действие.
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         // Определите IntentFilter.
        IntentFilter filter = new IntentFilter();
        // Добавить системные широковещательные действия, отправляемые системой при отключении питания
        // подключенный и отключенный.
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        // Зарегистрируйте получателя, используя контекст действия, передав
        // IntentFilter.
        this.registerReceiver(mReceiver, filter);

        // Зарегистрируйте приемник для получения пользовательской широковещательной передачи.
        LocalBroadcastManager.getInstance(this).registerReceiver
                (mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));
    }

    /**
     * Нажмите обработчик событий для кнопки, которая отправляет пользовательскую широковещательную рассылку с помощью
     * LocalBroadcastManager.
     */
    public void sendCustomBroadcast(View view) {
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this)
                .sendBroadcast(customBroadcastIntent);
    }

    /**
     * * Отмените регистрацию широковещательных приемников, когда приложение будет уничтожено.
     */
    @Override
    protected void onDestroy() {
// Отмените регистрацию получателей.
        this.unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
