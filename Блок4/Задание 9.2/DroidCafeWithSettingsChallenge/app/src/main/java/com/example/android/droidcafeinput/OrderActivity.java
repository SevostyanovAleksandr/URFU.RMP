package com.example.android.droidcafeinput;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Это действие обрабатывает переключатели для выбора
 * способ доставки заказа и элементы управления вводом EditText.
 */
public class OrderActivity extends AppCompatActivity {

    /**
     * Устанавливает для представления содержимого значение activity_order и получает намерение и его
     * данные.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

// Получить намерение и его данные.
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.order_textview);
        textView.setText(message);
    }

    /**
     * Проверяет, какой переключатель был нажат, и отображает всплывающее сообщение, чтобы
     * показать выбор.
     */
    public void onRadioButtonClicked(View view) {
// Кнопка теперь проверена?
        boolean checked = ((RadioButton) view).isChecked();
// Проверьте, какой переключатель был нажат.
        switch (view.getId()) {
            case R.id.sameday:
                if (checked)
                    // Обслуживание в тот же день
                    displayToast(
                            getString(R.string.same_day_messenger_service));
                break;
            case R.id.nextday:
                if (checked)
                    // Доставка на следующий день
                    displayToast(getString(R.string.next_day_ground_delivery));
                break;
            case R.id.pickup:
                if (checked)
                    // Забрать
                    displayToast(getString(R.string.pick_up));
                break;
            default:
                // Ничего не делай.
                break;
        }
    }

    /**
     * Отображает фактическое сообщение в виде всплывающего сообщения.
     */
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}
