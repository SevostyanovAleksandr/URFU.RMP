
package com.example.android.droidcafeinput;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Это приложение демонстрирует изображения, используемые в качестве кнопок, и плавающую кнопку действия для
 * намерение начать второе мероприятие. Приложение позволяет пользователю нажать на изображение, чтобы
 * сделать выбор. Приложение отображает тост, показывающий выбор пользователя, и отправляет
 * выбор в качестве данных с намерением запустить второе действие. Эта версия
 * включает опции в меню опций, в котором некоторые опции отображаются в
 виде значков * на панели приложений, а также включает кнопку Вверх.
 *
 * Эта версия также включает в себя опцию настроек. Пользователь нажимает кнопку Настройки
 * чтобы показать активность настроек и изменить настройки рынка.
 * При запуске приложение показывает тост, показывающий значение рыночной настройки.
 */
public class MainActivity extends AppCompatActivity {

    // Тег для дополнительного намерения.
    public static final String EXTRA_MESSAGE =
            "com.example.android.droidcafeinput.extra.MESSAGE";

    // Сообщение о заказе, отображаемое в тосте и отправляемое в новое действие.
    private String mOrderMessage;

    /**
     * Создает представление содержимого, панель инструментов и плавающую кнопку действия.
     *
     * Этот метод предусмотрен в базовом шаблоне действия.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        OrderActivity.class);
                intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                startActivity(intent);
            }
        });

// Устанавливает значения по умолчанию только один раз, при первом вызове. Третий
        // аргумент - это логическое значение, указывающее, являются ли значения по умолчанию
        // должно быть установлено более одного раза. При значении false система устанавливает значение по умолчанию
        // значения только при первом вызове.
        PreferenceManager.setDefaultValues(this,
                R.xml.pref_general, false);
        PreferenceManager.setDefaultValues(this,
                R.xml.pref_notification, false);
        PreferenceManager.setDefaultValues(this,
                R.xml.pref_account, false);

// Прочитайте настройки из общих настроек и отобразите тост.
        SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(this);
        String marketPref = sharedPref
                .getString("sync_frequency", "-1");
        displayToast(marketPref);
    }

    /**
     * Расширяет меню и добавляет элементы на панель действий, если она присутствует.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles app bar item clicks.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order:
                Intent intent = new Intent(MainActivity.this,
                        OrderActivity.class);
                intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this,
                        SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_status:
                displayToast(getString(R.string.action_status_message));
                return true;
            case R.id.action_favorites:
                displayToast(getString(R.string.action_favorites_message));
                return true;
            case R.id.action_contact:
                displayToast(getString(R.string.action_contact_message));
                return true;
            default:
                // Do nothing
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Отображает тост с сообщением.
     */
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * показывает сообщение о том, что изображение пончика было нажато.
     */
    public void showDonutOrder(View view) {
        mOrderMessage = getString(R.string.donut_order_message);
        displayToast(mOrderMessage);
    }

    /**
     * Shows a message that the ice cream sandwich image was clicked.
     */
    public void showIceCreamOrder(View view) {
        mOrderMessage = getString(R.string.ice_cream_order_message);
        displayToast(mOrderMessage);
    }

    /**
     * Показывает сообщение о том, что изображение froyo было нажато.
     */
    public void showFroyoOrder(View view) {
        mOrderMessage = getString(R.string.froyo_order_message);
        displayToast(mOrderMessage);
    }

}
