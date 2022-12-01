package com.android.fundamentals.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 Реализация широковещательного приемника, который выдает пользовательский тост
 сообщение, когда он получает любую из зарегистрированных трансляций.
 */
public class CustomReceiver extends BroadcastReceiver {

    // Строковая константа, определяющая пользовательское широковещательное действие.
    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";

    /**
     Этот метод обратного вызова вызывается, когда широковещательный приемник получает
     трансляция, на которую он зарегистрирован.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if (intentAction != null) {
            String toastMessage = context.getString(R.string.unknown_action);
            switch (intentAction){
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = context.getString(R.string.power_connected);
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage =
                            context.getString(R.string.power_disconnected);
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage =
                            context.getString(R.string.custom_broadcast_toast);
                    break;
            }

            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
