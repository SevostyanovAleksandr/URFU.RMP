package com.android.example.roomwordssample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_ID;
import static com.android.example.roomwordssample.MainActivity.EXTRA_DATA_UPDATE_WORD;

/**
 * Этот класс отображает экран, на котором пользователь вводит новое слово.
 * * NewWordActivity возвращает введенное слово вызывающему действию
 * (MainActivity), который затем сохраняет новое слово и обновляет список
 * отображаемых слов.
 */
public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.roomwordssample.REPLY";
    public static final String EXTRA_REPLY_ID = "com.android.example.roomwordssample.REPLY_ID";

    private EditText mEditWordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);

        mEditWordView = findViewById(R.id.edit_word);
        int id = -1 ;

        final Bundle extras = getIntent().getExtras();

// Если нам передается контент, заполните его для редактирования пользователем.
        if (extras != null) {
            String word = extras.getString(EXTRA_DATA_UPDATE_WORD, "");
            if (!word.isEmpty()) {
                mEditWordView.setText(word);
                mEditWordView.setSelection(word.length());
                mEditWordView.requestFocus();
            }
        } // В противном случае начните с пустых полей.


        final Button button = findViewById(R.id.button_save);

// Когда пользователь нажимает кнопку Сохранить, создайте новое намерение для ответа.
        // // Намерение ответа будет отправлено обратно вызывающему действию (в данном случае MainActivity).
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Создайте новое намерение для ответа.
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWordView.getText())) {
                    // Получить новое слово, введенное пользователем. // Ни одно слово не было введено, установите результат соответствующим образом.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Получить новое слово, введенное пользователем.
                    String word = mEditWordView.getText().toString();
                    // Добавьте новое слово в дополнение к намерению ответа.
                    replyIntent.putExtra(EXTRA_REPLY, word);
					if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    // Установите статус результата таким образом, чтобы он указывал на успех.
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
