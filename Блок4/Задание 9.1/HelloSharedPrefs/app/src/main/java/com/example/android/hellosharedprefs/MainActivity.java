package com.example.android.hellosharedprefs;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * * Hello SharedPrefs - это адаптация приложения HelloToast из главы 1.
 * Он включает в себя:
 * - Кнопки для изменения цвета фона.
 * - Поддержание состояния экземпляра.
 * - Темы и стили.
 * - Считывание и запись общих настроек для текущего количества и цвета.
 */
public class MainActivity extends AppCompatActivity {
    // Текущее количество
    private int mCount = 0;
    // Текущий цвет фона
    private int mColor;
    // Текстовый вид для отображения как количества, так и цвета
    private TextView mShowCountTextView;

    // Клавиша для текущего подсчета
    private final String COUNT_KEY = "count";
    // Клавиша для текущего цвета
    private final String COLOR_KEY = "color";

    // Объект общих предпочтений
    private SharedPreferences mPreferences;

    // Имя файла общих настроек
    private String sharedPrefFile =
            "com.example.android.hellosharedprefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализировать виды, цвет, предпочтения
        mShowCountTextView = findViewById(R.id.count_textview);
        mColor = ContextCompat.getColor(this,
                R.color.default_background);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        // Восстановить настройки
        mCount = mPreferences.getInt(COUNT_KEY, 0);
        mShowCountTextView.setText(String.format("%s", mCount));
        mColor = mPreferences.getInt(COLOR_KEY, mColor);
        mShowCountTextView.setBackgroundColor(mColor);
    }

    /**
     * Обрабатывает onClick для кнопок цвета фона.  Получает предысторию
     * цвет кнопки, которая была нажата, и задает фон TextView
     * к этому цвету.
     */
    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        mShowCountTextView.setBackgroundColor(color);
        mColor = color;
    }

    /**
     * Обрабатывает повторный щелчок для кнопки подсчета.  Увеличивает значение глобального параметра
     * mCount и обновляет текстовое представление.
     */
    public void countUp(View view) {
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));
    }

    /**
     * Обрабатывает повторный щелчок кнопки сброса.  Сбрасывает глобальное количество и
     * фоновые переменные на значения по умолчанию и сбрасывает представления на эти
     * значения по умолчанию.tes TextView.
     */
    public void reset(View view) {
        // Сброс количества
        mCount = 0;
        mShowCountTextView.setText(String.format("%s", mCount));

        // сброс цвета
        mColor = ContextCompat.getColor(this,
                R.color.default_background);
        mShowCountTextView.setBackgroundColor(mColor);

        // Четкие предпочтения
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

    /**
     * Обратный вызов для приостановки активности.  Общие настройки сохраняются здесь.
     */
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(COUNT_KEY, mCount);
        preferencesEditor.putInt(COLOR_KEY, mColor);
        preferencesEditor.apply();
    }
}
