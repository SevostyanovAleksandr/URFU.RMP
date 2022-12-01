
package com.example.android.twoactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
  Второе действие определяет второе действие в приложении. Он запущен
  из намерения с сообщением и отправляет намерение обратно со вторым
  сообщение.
 */
public class SecondActivity extends AppCompatActivity {
    // Логирование.
    private static final String LOG_TAG
            = SecondActivity.class.getSimpleName();
    // Уникальный тег для ответа с намерением.
    public static final String EXTRA_REPLY =
            "com.example.android.twoactivities.extra.REPLY";

    // Отредактируйте текст для ответа.
    private EditText mReply;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Инициализировать переменные представления.
        mReply = findViewById(R.id.editText_second);

        // Получить намерение, которое запустило это действие, и сообщение в
        // намерение дополнительное.
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Поместите это сообщение в text_message TextView.
        TextView textView = findViewById(R.id.text_message);
        textView.setText(message);
    }

    /**
     Обрабатывает повторный щелчок по кнопке "Ответить". Получает сообщение из
     второго EditText, создает намерение и возвращает это сообщение обратно к
     основному действию.

     */
    public void returnReply(View view) {
        // Получите ответное сообщение из отредактированного текста.
        String reply = mReply.getText().toString();

        // Создайте новое намерение для ответа, добавьте к нему ответное сообщение
        // в качестве дополнительного, установите результат намерения и закройте действие.
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        Log.d(LOG_TAG, "End SecondActivity");
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}
