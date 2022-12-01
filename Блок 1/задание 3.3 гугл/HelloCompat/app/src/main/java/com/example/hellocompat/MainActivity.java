package com.example.hellocompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {



//  Просмотр текста.
private TextView mHelloTextView;
// массив названий цветов, они соответствуют цветовым ресурсам в color.xml
private String[] mColorArray = {"red", "pink", "purple", "deep_purple",
        "indigo", "blue", "light_blue", "cyan", "teal", "green",
        "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
        "brown", "grey", "blue_grey", "black" };
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloTextView = findViewById(R.id.hello_textview);
        //восстановить сохраненное состояние экземпляра (цвет текста)
        if (savedInstanceState != null) {
        mHelloTextView.setTextColor(savedInstanceState.getInt("color"));
        }
        }
        public void changeColor(View view) {
                Random random = new Random();
                String colorName = mColorArray[random.nextInt(20)];
                int colorResourceName = getResources().getIdentifier(colorName, "color", getApplicationContext().getPackageName());
                int colorRes = ContextCompat.getColor(this, colorResourceName);
                mHelloTextView.setTextColor(colorRes);

        }
@Override
public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // сохраните текущий цвет текста
        outState.putInt("color", mHelloTextView.getCurrentTextColor());
        }
}

