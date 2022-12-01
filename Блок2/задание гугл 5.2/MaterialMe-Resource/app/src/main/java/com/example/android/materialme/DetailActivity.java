package com.example.android.materialme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

/**
 Подробное действие, которое запускается при нажатии на элемент списка.
 Он показывает больше информации о виде спорта.
 */
public class DetailActivity extends AppCompatActivity {

    /**
     Инициализирует действие, заполняя данные из намерения.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Инициализируйте представления.
        TextView sportsTitle = findViewById(R.id.titleDetail);
        ImageView sportsImage = findViewById(R.id.sportsImageDetail);

        // Установите текст из списка намерений дополнительно.
        sportsTitle.setText(getIntent().getStringExtra("title"));

// Загрузите изображение, используя библиотеку Glide и Intent extra.
        Glide.with(this)
                .load(getIntent()
                        .getIntExtra("image_resource",0))
                .into(sportsImage);
    }
}
