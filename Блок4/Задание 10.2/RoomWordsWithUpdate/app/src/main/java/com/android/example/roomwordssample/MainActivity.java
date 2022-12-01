package com.android.example.roomwordssample;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Этот класс отображает список слов в RecyclerView.
 * Слова сохраняются в базе данных комнат.
 * Макет для этого действия также отображает FAB, который
 * * позволяет пользователям запускать действие "Новое слово", чтобы добавлять новые слова.
 * Пользователи могут удалить слово, проведя по нему пальцем, или удалить все слова
 * через меню опций.
 * Всякий раз, когда новое слово добавляется, удаляется или обновляется, RecyclerView
 * отображение списка слов автоматически обновляется.
 */
public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_WORD = "extra_word_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Настройте RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Настройте WordViewModel.
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
// Получите все слова из базыданных
// и свяжите их с адаптером.
        mWordViewModel.getAllWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable final List<Word> words) {
                // Обновите кэшированную копию слов в адаптере.
                adapter.setWords(words);
            }
        });

        // Настройка плавающей кнопки действия
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

// Добавьте функциональность для пролистывания элементов в
// RecyclerView, чтобы удалить пролистанный элемент.
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // // Мы не реализуем onMove() в этом приложении.
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
// // Когда пользователь проводит пальцем по слову,
// удалите это слово из базы данных.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word myWord = adapter.getWordAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete_word_preamble) + " " +
                                myWord.getWord(), Toast.LENGTH_LONG).show();

                        // Опустите это слово.
                        mWordViewModel.deleteWord(myWord);
                    }
                });
// // Прикрепите элемент touch helper к виду recycler.
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new WordListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Word word = adapter.getWordAtPosition(position);
                launchUpdateWordActivity(word);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Раздуть меню; это добавляет элементы на панель действий, если она присутствует.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // В меню опций есть один пункт "Очистить все данные сейчас".
    // который удаляет все записи в базе данных.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Обработайте элемент панели действий, нажав здесь. Панель действий будет
        // автоматически обрабатывать нажатия на кнопку "Домой"/"Вверх", пока
        // как вы указываете родительское действие в AndroidManifest.xml .
        int id = item.getItemId();

//отсутствие проверки, упрощаемое утверждение
        if (id == R.id.clear_data) {
// Добавьте тост только для подтверждения
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

// Удалите существующие данные.
            mWordViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * * Когда пользователь вводит новое слово в NewWordActivity,
     * это действие возвращает результат этому действию.
     * Если пользователь ввел новое слово, сохраните его в базе данных.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            // Сохраните данные.
            mWordViewModel.insert(word);
        } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String word_data = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            int id = data.getIntExtra(NewWordActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                mWordViewModel.update(new Word(id, word_data));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateWordActivity( Word word) {
        Intent intent = new Intent(this, NewWordActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_WORD, word.getWord());
        intent.putExtra(EXTRA_DATA_ID, word.getId());
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }
}