package com.example.android.materialme;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/***
 Основное действие приложения Material Me, имитирующего приложение для спортивных новостей.
 */
public class MainActivity extends AppCompatActivity {

    // Переменные-члены.
    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализируйте RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Получить соответствующее количество столбцов.
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

// Установите менеджер компоновки.
        mRecyclerView.setLayoutManager(new GridLayoutManager(
                this, gridColumnCount));

// Инициализируйте ArrayList, который будет содержать данные.
        mSportsData = new ArrayList<>();

        // Инициализируйте адаптер и установите его в RecyclerView.
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

// Получить данные.
        initializeData();

// Если имеется более одного столбца, отключите свайп, чтобы отклонить
        int swipeDirs;
        if(gridColumnCount > 1){
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        // Вспомогательный класс для создания салфетки для удаления и перетаскивания
        // функциональность
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper
                .SimpleCallback(
                  ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | 
                  ItemTouchHelper.DOWN | ItemTouchHelper.UP,
                swipeDirs) {
            /**
             Определяет функциональность перетаскивания.
             */
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // Получить позиции "от" и "до".
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                // Поменяйте местами элементы и уведомите адаптер.
                Collections.swap(mSportsData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             Определяет свайп для отключения функциональности.
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                 int direction) {
// Удалить элемент из набора данных.
                mSportsData.remove(viewHolder.getAdapterPosition());
                // Уведомить адаптер.
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        // Прикрепите помощник к RecyclerView.
        helper.attachToRecyclerView(mRecyclerView);
    }

    /**
     Инициализируйте спортивные данные из ресурсов.
     */
    private void initializeData() {
        // Получите ресурсы из XML-файла.
        String[] sportsList = getResources()
                .getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources()
                .getStringArray(R.array.sports_info);
        TypedArray sportsImageResources =
                getResources().obtainTypedArray(R.array.sports_images);

// Очистите существующие данные (чтобы избежать дублирования).
        mSportsData.clear();

// Создайте ArrayList спортивных объектов с названиями и
// информацией о каждом виде спорта.
        for (int i = 0; i < sportsList.length; i++) {
            mSportsData.add(new Sport(sportsList[i], sportsInfo[i],
                    sportsImageResources.getResourceId(i, 0)));
        }

// Переработать введенный массив.
        sportsImageResources.recycle();

// Уведомить адаптер об изменении.
        mAdapter.notifyDataSetChanged();
    }

    /**
     Метод onClick для th FAB, который сбрасывает данные.
     */
    public void resetSports(View view) {
        initializeData();
    }
}
