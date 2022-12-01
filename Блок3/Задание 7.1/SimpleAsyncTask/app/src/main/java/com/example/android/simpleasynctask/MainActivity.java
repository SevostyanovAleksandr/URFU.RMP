package com.example.android.simpleasynctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 Приложение SimpleAsyncTask содержит кнопку, которая запускает AsyncTask
 */

public class MainActivity extends AppCompatActivity {

    // Клавиша для сохранения состояния TextView
    private static final String TEXT_STATE = "currentText";

    // Текстовое представление, в котором мы будем показывать результаты
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);

        // Восстановите TextView, если есть пакет savedInstanceState.
        if (savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    /**
     * Обрабатывает нажатие кнопки "Запустить задачу". Запускает AsyncTask
     */
    public void startTask(View view) {
        // Поместите сообщение в текстовое представление.
        mTextView.setText(R.string.napping);
        //AsyncTask есть обратный вызов, который обновит текстовое представление.
        new SimpleAsyncTask(mTextView).execute();
    }

    /**
     * Сохраняет содержимое TextView для восстановления при изменении конфигурации.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Сохранить состояние текстового представления
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }
}
