package com.example.android.recyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.LinkedList;

/**
 Реализует базовый RecyclerView, который отображает список сгенерированных слов.
 Щелчок по элементу помечает его как щелкнутый.
 Нажатие кнопки fab добавляет новое слово в список.
 */
public class MainActivity extends AppCompatActivity {

    private final LinkedList<String> mWordList = new LinkedList<>();

    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

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
                int wordListSize = mWordList.size();
                // Добавьте новое слово в список слов.
                mWordList.addLast(" Клик " + wordListSize);
                // Уведомить адаптер о том, что данные изменились.
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                // Прокрутите страницу вниз.
                mRecyclerView.smoothScrollToPosition(wordListSize);
            }
        });

        // Поместите исходные данные в список слов.
        for (int i = 0; i < 20; i++) {
            mWordList.addLast("Word " + i);
        }

        // Создайте представление утилизатора.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Создайте адаптер и укажите данные, которые будут отображаться.
        mAdapter = new WordListAdapter(this, mWordList);
        // Подключите адаптер с видом на утилизатор.
        mRecyclerView.setAdapter(mAdapter);
        // Предоставьте виду recycler диспетчер компоновки по умолчанию.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     Расширяет меню и добавляет элементы на панель действий, если она присутствует.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Раздуть меню; это добавляет элементы на панель действий, если она присутствует.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Обрабатывает щелчки по элементам панели приложений.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Обработайте элемент панели действий, нажав здесь. Панель действий будет
// автоматически обрабатывать нажатия на кнопку "Домой"/"Вверх", пока
// как вы указываете родительское действие в AndroidManifest.xml .
        int id = item.getItemId();

// Этот комментарий подавляет предупреждение Android Studio об упрощении
// операторы возврата.
//отсутствие проверки, упрощаемое утверждение
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
